package com.neuqer.fitornot.base.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import butterknife.ButterKnife;

/**
 * @author DuLong
 * @since 2019/8/2
 * email 798382030@qq.com
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViewSparseArray;
    private Context mContext;
    private View mItemView;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mItemView = itemView;
        mViewSparseArray = new SparseArray<>();
        ButterKnife.bind(this, itemView);
    }

    /**
     * 从布局获得View
     *
     * @param viewId
     * @param <T>
     * @retrun
     */
    public  <T extends View> T findViewById(@IdRes int viewId) {
        //从已经缓存的viewMap中查找
        View view = mViewSparseArray.get(viewId);
        //没有缓存，获得实例加入缓存
        if(view == null) {
            view = mItemView.findViewById(viewId);
            mViewSparseArray.append(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView类型
     *
     * @param viewId
     * @param value
     * @return
     */
    public BaseViewHolder setText(@IdRes int viewId, @StringRes int value) {
        return setText(viewId, mContext.getString(value));
    }

    public BaseViewHolder setText(@IdRes int viewId, String value) {
        TextView textView = findViewById(viewId);
        textView.setText(value == null ? "" : value);
        return this;
    }

    /**
     * 设置ImageView的url
     *
     * @param viewId
     * @param value
     * @return
     */
    public BaseViewHolder setImgUrl(@IdRes int viewId, String value) {
        ImageView imageView = findViewById(viewId);
        if (value != null) {
            Glide.with(mContext)
                    .load(value)
                    .transition(GenericTransitionOptions.<android.graphics.drawable.Drawable>withNoTransition())
                    .into(imageView);
        }
        return this;
    }

    /**
     * 设置圆形图片
     * @param viewId
     * @param value
     * @return
     */
    public BaseViewHolder setCircleImgUrl(int viewId, String value){
        ImageView imageView = findViewById(viewId);
        if(value != null){
            Glide.with(mContext.getApplicationContext())
                    .load(value)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(imageView);
        }

        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public BaseViewHolder setImgRes(@IdRes int viewId, @DrawableRes int drawableId) {
        ImageView imageView = findViewById(viewId);
        imageView.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置控件是否可见
     *
     * @param viewId
     * @param isVisibility
     * @return
     */
    public BaseViewHolder setVisible(@IdRes int viewId, boolean isVisibility) {
        findViewById(viewId).setVisibility(isVisibility ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    /**
     * 设置本地存储的uri到图片
     * @param viewId
     * @param value
     * @return
     */
    public BaseViewHolder setLocalImgRes(@IdRes int viewId, String value) {
        File file = new File(value);
        ImageView img = findViewById(viewId);
        Glide.with(mContext).load(file).into(img);
        return this;
    }

    /**
     * 设置控件是否消失（不占空间）
     *
     */
    public BaseViewHolder setGone(@IdRes int viewId) {
        findViewById(viewId).setVisibility(View.GONE);
        return this;
    }


    /**
     * 获取item的View
     *
     * @return
     */
    public View getmItemView() {
        return mItemView;
    }

    /**
     * 获取当前viewHolder位置
     *
     * @return
     */
    public int getViewHolderPosition() {
        return getAdapterPosition();
    }

}
