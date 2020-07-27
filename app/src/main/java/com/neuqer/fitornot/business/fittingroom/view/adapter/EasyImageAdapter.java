package com.neuqer.fitornot.business.fittingroom.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.base.adapter.BaseViewHolder;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/9/28
 * email 798382030@qq.com
 */

public class EasyImageAdapter extends BaseRecyclerViewAdapter<Bitmap> {


    public EasyImageAdapter(Context context, List<Bitmap> dataList, int itemLayoutId) {
        super(context, dataList, itemLayoutId);

    }

    @Override
    protected void bind(BaseViewHolder holder, Bitmap mDrawable) {
        holder.setImgRes(R.id.img_android, R.mipmap.ic_launcher);
    }



}

