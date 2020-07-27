package com.neuqer.fitornot.business.fittingroom.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.audiofx.DynamicsProcessing;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.fittingroom.view.activity.ChangeBackGroundActivity;
import com.neuqer.fitornot.business.fittingroom.view.activity.CollocationActivity;
import com.neuqer.fitornot.util.Loader.ImageLoader;

import java.util.List;

import retrofit2.http.POST;

/**
 * @author DuLong
 * @since 2019/10/2
 * email 798382030@qq.com
 */

public class BackGroundRecyclerViewAdapter extends RecyclerView.Adapter<BackGroundRecyclerViewAdapter.BaseHolder> {

    private Context mContext;
    private List<String[]> mData;
    private ImageLoader mLoader;
    private int width;
    private int height;


    public BackGroundRecyclerViewAdapter(Context mContext, List<String[]> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mLoader = ImageLoader.build(mContext);
    }

    private static final int LARGE = 1;
    private static final int SMALL = 0;
    @NonNull
    @Override
    /**
     * 动态的改变item的宽高
     */
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup mViewGroup, int type) {
        View mView;
        if (type == LARGE) {
            mView = LayoutInflater.from(mViewGroup.getContext()).inflate(R.layout.item_easy_background_image_large, mViewGroup, false);
        } else {
            mView = LayoutInflater.from(mViewGroup.getContext()).inflate(R.layout.item_easy_backgroung_image_small, mViewGroup, false);
        }
        int parentHight = mViewGroup.getHeight();
        ViewGroup.LayoutParams params = mView.getLayoutParams();
        params.height = (int) (parentHight / 3.5);
        width = mViewGroup.getWidth() / 3;
        height = (int) (parentHight / 3.5);
        return new BaseHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder mBaseHolder, int position) {
        mLoader.bindBitmap(mData.get(position)[0], mBaseHolder.leftImage, width, height);
        if (!mData.get(position)[1].equals("")) {
            mLoader.bindBitmap(mData.get(position)[1], mBaseHolder.rightImage, width, height);
            mBaseHolder.rightImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //将选项传回CollocationActivity中
                    Intent mIntent = new Intent();
                    mIntent.putExtra("background", mData.get(position)[1]);
                    ((ChangeBackGroundActivity) mContext).setResult(Activity.RESULT_OK, mIntent);
                    ((ChangeBackGroundActivity) mContext).finish();
                }
            });
        }


        mBaseHolder.leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将选项传回CollocationActivity中
                Intent mIntent = new Intent();
                mIntent.putExtra("background", mData.get(position)[0]);
                ((ChangeBackGroundActivity) mContext).setResult(Activity.RESULT_OK, mIntent);
                ((ChangeBackGroundActivity) mContext).finish();
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 更具类型决定该采用哪种ViewHolder
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return SMALL;
        } else {
            return LARGE;
        }
    }

    public class BaseHolder extends RecyclerView.ViewHolder {
        ImageView leftImage;
        ImageView rightImage;
        BaseHolder(@NonNull View itemView) {
            super(itemView);
            leftImage = itemView.findViewById(R.id.img_left_photo);
            rightImage = itemView.findViewById(R.id.img_right_photo);
        }
    }

}
