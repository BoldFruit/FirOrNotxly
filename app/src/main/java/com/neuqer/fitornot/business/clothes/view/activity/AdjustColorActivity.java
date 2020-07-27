package com.neuqer.fitornot.business.clothes.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.business.clothes.view.ClothesFragment;
import com.neuqer.fitornot.business.clothes.view.adapter.BitmapAdapter;
import com.neuqer.fitornot.business.mine.view.MineFragment;
import com.neuqer.fitornot.util.Loader.ImageLoader;
import com.neuqer.fitornot.util.StatusBarUtils;

public class AdjustColorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View
        .OnClickListener{

    private SeekBar mseekbar_hue;
    private SeekBar mseekbar_lum;
    private SeekBar mseekbar_saturation;
    private ImageView imageView;
    private float mhue,msaturation,mlum;
    private static int MID_VALUE=127;
    private Bitmap mbitmap;
    private ImageView mReture;
    private ImageView mYes;

    public static void actionStart(Context mContext) {
        Intent mIntent = new Intent(mContext, AdjustColorActivity.class);
        ((Activity)mContext).startActivityForResult(mIntent, ClothesFragment.ADJUST_COLOR);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_color);
        //初始化
        mhue = MID_VALUE;
        msaturation = MID_VALUE;
        mlum = MID_VALUE;
        StatusBarUtils.setStatusBarMode(this, false, R.color.fitting_toolbar_blue);
        byte[] data = BitmapAdapter.bitmapBytes;
        mbitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        mReture = findViewById(R.id.img_return_color);
        mReture.setOnClickListener(this);
        mYes = findViewById(R.id.img_yes_color);
        mYes.setOnClickListener(this);
        mseekbar_hue= (SeekBar) findViewById(R.id.seekBar);
        mseekbar_saturation= (SeekBar) findViewById(R.id.seekBar1);
        mseekbar_lum= (SeekBar) findViewById(R.id.seekBar2);
        imageView= (ImageView) findViewById(R.id.image1);
        imageView.setImageBitmap(mbitmap);
        //对SeekBar设置最大值与初始值
        mseekbar_hue.setMax(255);
        mseekbar_hue.setProgress(MID_VALUE);
        //对seekbar的改变就行监听
        mseekbar_hue.setOnSeekBarChangeListener(this);
        mseekbar_saturation.setMax(255);
        mseekbar_saturation.setProgress(MID_VALUE);
        mseekbar_saturation.setOnSeekBarChangeListener(this);
        mseekbar_lum.setMax(255);
        mseekbar_lum.setProgress(MID_VALUE);
        mseekbar_lum.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar mSeekBar, int mI, boolean mB) {
        switch (mSeekBar.getId()){
            case R.id.seekBar:
               mhue=(mI-MID_VALUE)*1.0f/255*180;
                break;
            case R.id.seekBar1:
                msaturation=mI*1.0f/MID_VALUE;
                break;
            case R.id.seekBar2:
                mlum=mI*1.0f/MID_VALUE;
                break;

        }
        imageView.setImageBitmap(ImageOperation.imageoperation(mbitmap, mhue, msaturation, mlum));
    }

    @Override
    public void onStartTrackingTouch(SeekBar mSeekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar mSeekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return_color:
                finish();
                break;
            case R.id.img_yes_color:
                Intent mIntent = new Intent();
                if (mhue == msaturation && mhue == msaturation && mhue == MID_VALUE) {
                    mIntent.putExtra("bitmap", ImageLoader.decodeBitmapToByteArrayNoCut(mbitmap));
                } else {
                    mIntent.putExtra("bitmap", ImageLoader.decodeBitmapToByteArrayNoCut(ImageOperation.imageoperation(mbitmap, mhue, msaturation, mlum)));
                }
                setResult(RESULT_OK, mIntent);
                finish();
        }
    }

    public static class ImageOperation {
        //传入需要修改的Bitmap和色彩三元素
        public static Bitmap imageoperation (Bitmap mbitmap , float hue, float saturation, float lum){
            //传入的Bitmap默认不可修改，需啊哟创建新的Bitmap
            Bitmap mbitmap_fu=Bitmap.createBitmap(mbitmap.getWidth(),mbitmap.getHeight(), Bitmap.Config.ARGB_8888);
            //创建画布，在新的bitmap上绘制
            Canvas canvas=new Canvas(mbitmap_fu);
            //设置画笔抗锯齿，后面在Bitmap上绘制需要使用到画笔
            Paint mpaint=new Paint(Paint.ANTI_ALIAS_FLAG);

            ColorMatrix huematrix=new ColorMatrix();
            huematrix.setRotate(0,hue);
            huematrix.setRotate(1,hue);
            huematrix.setRotate(2, hue);

            ColorMatrix saturationmatrix=new ColorMatrix();
            saturationmatrix.setSaturation(saturation);

            ColorMatrix lummatrix=new ColorMatrix();
            //参数：rscale gscale bscale 透明度
            lummatrix.setScale(lum,lum,lum,1);

            ColorMatrix imagematrix=new ColorMatrix();
            imagematrix.postConcat(huematrix);
            imagematrix.postConcat(saturationmatrix);
            imagematrix.postConcat(lummatrix);
            //通过画笔的setColorFilter进行设置
            mpaint.setColorFilter(new ColorMatrixColorFilter(imagematrix));
            canvas.drawBitmap(mbitmap,0,0,mpaint);
            return mbitmap_fu;
        }
    }
}
