package com.neuqer.fitornot.util.Loader;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.util.ToastUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageLoader {

    private static final String TAG = "ImageLoader";

    public static final int MESSAGE_POST_RESULT = 1;

    private static final int CPU_COUNT = Runtime.getRuntime()
            .availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;

    private static final int TAG_KEY_URI = R.id.tag_first;
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 1024;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CACHE_INDEX = 0;
    private boolean mIsDiskLruCacheCreated = false;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };

    //创建线程池
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), sThreadFactory);

    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            imageView.setImageBitmap(result.bitmap);
            String uri = (String) imageView.getTag(TAG_KEY_URI);
            if (uri.equals(result.uri)) {
                imageView.setImageBitmap(result.bitmap);
            } else {
                Log.w(TAG, "set image bitmap,but url has changed, ignored!");
            }
        }

        ;
    };

    private Context mContext;
    private ImageResizer mImageResizer = new ImageResizer();
    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private ImageLoader(Context context) {
        mContext = context.getApplicationContext();
        //设置缓存大小为内存的八分之一
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
        //初始化外部储存
        File diskCacheDir = getDiskCacheDir(mContext, "fitornot");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        //判断剩余空间是否小于所需空间
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1,
                        DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * build a new instance of ImageLoader
     *
     * @param context
     * @return a new instance of ImageLoader
     */
    public static ImageLoader build(Context context) {
        return new ImageLoader(context);
    }

    //添加到内存
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    //查找内存
    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * load bitmap from memory cache or disk cache or network async, then bind imageView and bitmap.
     * NOTE THAT: should run in UI Thread
     *
     * @param uri       http url
     * @param imageView bitmap's bind object
     */
    public void bindBitmap(final String uri, final ImageView imageView) {
        bindBitmap(uri, imageView, 0, 0);
    }

    //加载uri并赋值给imageView
    public void bindBitmap(final String uri, final ImageView imageView,
                           final int reqWidth, final int reqHeight) {
        imageView.setTag(TAG_KEY_URI, uri);
        //先从内存中查找是否有缓存
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        //异步处理
        Runnable loadBitmapTask = new Runnable() {

            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri, reqWidth, reqHeight);
                if (bitmap != null) {
                    LoaderResult result = new LoaderResult(imageView, uri, bitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }


    /**
     * load bitmap from memory cache or disk cache or network.
     *
     * @param uri       http url
     * @param reqWidth  the width ImageView desired
     * @param reqHeight the height ImageView desired
     * @return bitmap, maybe null.
     */
    public Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
        //在内存中查找缓存
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null) {
            Log.d(TAG, "loadBitmapFromMemCache,url:" + uri);
            return bitmap;
        }

        try {
            //在DiskCache中查找缓存
            bitmap = loadBitmapFromDiskCache(uri, reqWidth, reqHeight);
            if (bitmap != null) {
                Log.d(TAG, "loadBitmapFromDisk,url:" + uri);
                return bitmap;
            }
            //通过网络请求获得bitmap,并存储到LruCache
            bitmap = loadBitmapFromHttp(uri, reqWidth, reqHeight);
            Log.d(TAG, "loadBitmapFromHttp,url:" + uri);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //直接网络请求
        if (bitmap == null && !mIsDiskLruCacheCreated) {
            Log.w(TAG, "encounter error, DiskLruCache is not created.");
            bitmap = downloadBitmapFromUrl(uri);
        }

        return bitmap;
    }

    private Bitmap loadBitmapFromMemCache(String url) {
        final String key = hashKeyFormUrl(url);
        Bitmap bitmap = getBitmapFromMemCache(key);
        return bitmap;
    }

    //存储图片到DiskLruCache
    private Bitmap loadBitmapFromHttp(String url, int reqWidth, int reqHeight)
            throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network from UI Thread.");
        }
        if (mDiskLruCache == null) {
            return null;
        }

        //通过url的MD5值创建唯一标识
        String key = hashKeyFormUrl(url);
        //存储在DisklruCache
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (downloadUrlToStream(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(url, reqWidth, reqHeight);
    }


    //从外部内存进行查找,查找到就加入内存
    private Bitmap loadBitmapFromDiskCache(String url, int reqWidth,
                                           int reqHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w(TAG, "load bitmap from UI Thread, it's not recommended!");
        }
        if (mDiskLruCache == null) {
            return null;
        }

        Bitmap bitmap = null;
        String key = hashKeyFormUrl(url);
        DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
        if (snapShot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapShot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            bitmap = mImageResizer.decodeSampledBitmapFromFileDescriptor(fileDescriptor,
                    reqWidth, reqHeight);
            if (bitmap != null) {
                addBitmapToMemoryCache(key, bitmap);
            }
        }

        return bitmap;
    }

    //通过网络请求得到相应的OutputStream，存储到LruCache
    public boolean downloadUrlToStream(String urlString,
                                       OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            Log.e(TAG, "downloadBitmap failed." + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            MyUtils.close(out);
            MyUtils.close(in);
        }
        return false;
    }

    //通过网络请求得到bitmap
    private Bitmap downloadBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        try {

            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (final IOException e) {
            Log.e(TAG, "Error in downloadBitmap: " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            MyUtils.close(in);
        }
        return bitmap;
    }

    //获得Url的MD5值
    private String hashKeyFormUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 创建外部存储的一个目录
     *
     * @param context
     * @param uniqueName 目录的名字
     * @return 目录的绝对路径
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalStorageAvailable = Environment
                .getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + uniqueName);
    }

    @TargetApi(VERSION_CODES.GINGERBREAD)
    private long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return stats.getBlockSizeLong() * stats.getAvailableBlocksLong();
    }

    private static class LoaderResult {
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }


    /**
     * 随机生产文件名
     *
     * @return
     */
    public String generateFileName() {
        //通过时间为图片命名
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        return filename;
    }

    /**
     * 返回存储图片的地址
     *
     * @return
     */
    public File getSavePath() {
        String savePath;
        //判断是否外部储存可用
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = mContext.getExternalCacheDir().getAbsolutePath() + "/pic";
        } else {
            savePath = mContext.getCacheDir().getAbsolutePath() + "/pic";
        }
        File filePic = new File(savePath);
        if (!filePic.exists()) {
            filePic.mkdir();
        }
        return filePic;
    }

    /**
     * 保存bitmap到本地
     *
     * @param mBitmap
     * @return
     */
    public String saveBitmap(Bitmap mBitmap) {
        String savePath;
        File filePic;

        savePath = getSavePath().getAbsolutePath();
        try {
            filePic = new File(savePath + File.separator + generateFileName() + ".png");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdir();
                filePic.createNewFile();
            }

            //将bitmap导入文件中
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    /**
     * 保存图片到相册
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public void saveBmp2Gallery(Bitmap bmp, String picName) {

        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;

        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;
        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //通知相册更新
        MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);


    }

    /**
     * 将图片转化为字节数组
     *
     * @param mBitmap
     */
    public static byte[] decodeBitmapToByteArray(Bitmap mBitmap) {
        ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, mOutputStream);
        return mOutputStream.toByteArray();
    }

    public static byte[] decodeBitmapToByteArrayNoCut(Bitmap mBitmap) {
        ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 80, mOutputStream);
        return mOutputStream.toByteArray();
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 通过Uri获取文件
     *
     * @param ac
     * @param uri
     * @return
     */
    public static File getFileFromMediaUri(Context ac, Uri uri) {
        if (uri.getScheme().toString().compareTo("content") == 0) {
            ContentResolver cr = ac.getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);// 根据Uri从数据库中找
            if (cursor != null) {
                cursor.moveToFirst();
                String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路径
                cursor.close();
                if (filePath != null) {
                    return new File(filePath);
                }
            }
        } else if (uri.getScheme().toString().compareTo("file") == 0) {
            return new File(uri.toString().replace("file://", ""));
        }
        return null;
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;

    }


    /**
     * 通过uri获取图片并进行压缩
     *一般
     * @param uri
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }


    /**压缩图片
     *最厉害
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        if (bm == null) {
            return null;
        }
        int degree = readPictureDegree(filePath);
        bm = rotateBitmap(bm, degree);
        ByteArrayOutputStream baos = null;
        return bm;
    }

    public static Bitmap getSmallBitmapByBitmap(Bitmap mBitmap) {
        int ritio = calculateInSampleSizeByBitmap(mBitmap, 480, 800);
        mBitmap.setHeight(mBitmap.getHeight() / ritio);
        mBitmap.setWidth(mBitmap.getWidth()/ ritio);
        return mBitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }
    private static int calculateInSampleSizeByBitmap(Bitmap mBitmap,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = mBitmap.getHeight();
        final int width = mBitmap.getWidth();
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }


    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    private static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

}
