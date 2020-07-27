package com.neuqer.fitornot.business.mine.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.base.adapter.BaseViewHolder;
import com.neuqer.fitornot.business.mine.model.response.FollowedModel;
import com.neuqer.fitornot.business.mine.model.response.FollowingModel;

import java.util.List;

/**
 * Author: Kingtous
 * Since: 2019-10-06
 * Email: me@kingtous.cn
 */
public class MyFollowingAdapter extends BaseRecyclerViewAdapter<FollowingModel.DataBean> {

    public MyFollowingAdapter(Context context, List<FollowingModel.DataBean> userInfoModels, int itemLayoutId) {
        super(context, userInfoModels, itemLayoutId);
    }

    public interface SetButtonClickListener{
        void onButtonClicked(FollowingModel.DataBean dataBean);
    }

    public SetButtonClickListener mButtonClickListener;
    public void setButtonClickListener(SetButtonClickListener listener){
        this.mButtonClickListener = listener;
    }

    @Override
    protected void bind(BaseViewHolder holder, FollowingModel.DataBean dataBean) {
        //        holder.setText(R.id.txt_follow_number,String.valueOf(dataBean.getData());
//        holder.setText(R.id.txt_follower_number,String.valueOf(model.getFollowers()));
        holder.setImgUrl(R.id.img_avatar,dataBean.getAvatar_url());
        holder.setText(R.id.txt_avatar,dataBean.getNickname());
        holder.setText(R.id.txt_follow_number,String.valueOf(dataBean.getFollowing()));
        holder.setText(R.id.txt_follower_number,String.valueOf(dataBean.getFollowers()));

        // 监听
        if (mButtonClickListener!=null){
            holder.getmItemView().findViewById(R.id.btn_accept_request).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mButtonClickListener.onButtonClicked(dataBean);
                }
            });
        }
    }
}
