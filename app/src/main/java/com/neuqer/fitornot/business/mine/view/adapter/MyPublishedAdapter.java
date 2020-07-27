package com.neuqer.fitornot.business.mine.view.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.base.adapter.BaseViewHolder;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.business.mine.model.response.UserInfoModel;
import com.qmuiteam.qmui.layout.QMUIFrameLayout;

import java.util.List;

/**
 * Author: Kingtous
 * Since: 2019-10-02
 * Email: me@kingtous.cn
 */
public class MyPublishedAdapter extends BaseRecyclerViewAdapter<MomentsModel.DataBean> {

    public MyPublishedAdapter(Context context, List<MomentsModel.DataBean> models, int itemLayoutId) {
        super(context, models, itemLayoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, MomentsModel.DataBean dataBean) {
        // 页面设置
        QMUIFrameLayout frameLayout = (holder.getmItemView().findViewById(R.id.flayout_my_published));
        frameLayout.setShadowAlpha((float) 0.5);
        frameLayout.setShadowElevation((int) mContext.getResources().getDimension(R.dimen.p4));
        frameLayout.setRadius((int) mContext.getResources().getDimension(R.dimen.p1));

        holder.setImgUrl(R.id.img_my_published,dataBean.getPic_url());
        holder.setText(R.id.txt_my_published,dataBean.getContent());
    }

}
