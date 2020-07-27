package com.neuqer.fitornot.business.mine.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.base.adapter.BaseViewHolder;
import com.neuqer.fitornot.business.mine.model.response.UserInfoModel;

import java.util.List;

/**
 * Author: Kingtous
 * Since: 2019-10-01
 * Email: me@kingtous.cn
 */
public class FitRequestAdapter extends BaseRecyclerViewAdapter<UserInfoModel> {

    public FitRequestAdapter(Context context, List<UserInfoModel> userInfoModels, int itemLayoutId) {
        super(context, userInfoModels, itemLayoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, UserInfoModel model) {
        holder.setText(R.id.txt_fit_request_description,model.getSignature());
        holder.setText(R.id.txt_avatar,model.getNickname());
        holder.setImgUrl(R.id.img_avatar,model.getAvatar_url());
    }


    @Override
    public BaseViewHolder onCreateViewHodler(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_fit_request,parent);
        return new BaseViewHolder(mContext,view);
    }

}
