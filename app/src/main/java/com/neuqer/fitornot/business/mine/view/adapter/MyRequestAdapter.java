package com.neuqer.fitornot.business.mine.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.base.adapter.BaseViewHolder;
import com.neuqer.fitornot.business.mine.model.response.UserInfoModel;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButtonDrawable;

import java.util.List;

/**
 * Author: Kingtous
 * Since: 2019-10-01
 * Email: me@kingtous.cn
 */
public class MyRequestAdapter extends BaseRecyclerViewAdapter<UserInfoModel> {

    public MyRequestAdapter(Context context, List<UserInfoModel> userInfoModels, int itemLayoutId) {
        super(context, userInfoModels, itemLayoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, UserInfoModel model) {
        holder.setText(R.id.txt_fit_request_description,model.getSignature());
        holder.setText(R.id.txt_avatar,model.getNickname());
        holder.setImgUrl(R.id.img_avatar,model.getAvatar_url());

        // 请求状态
        int status = model.getFollowing();
        if (status == 1){
            // 请求完成
            QMUIRoundButton btn_request_status = holder.getmItemView().findViewById(R.id.btn_request_status);
            btn_request_status.setText("搭配完成");
            btn_request_status.setTextColor(mContext.getResources().getColor(R.color.dialog_enter_green));
            ((QMUIRoundButtonDrawable)btn_request_status.getBackground()).setStroke(1,mContext.getResources().getColor(R.color.dialog_enter_green));
            // 显示详情按钮
            ImageView btn_detail = holder.getmItemView().findViewById(R.id.btn_detail);
            btn_detail.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public BaseViewHolder onCreateViewHodler(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_my_request,parent);
        return new BaseViewHolder(mContext,view);
    }

}
