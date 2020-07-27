package com.neuqer.fitornot.business.circle.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.base.adapter.BaseViewHolder;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.util.ToastUtil;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.List;

public class CardAdapter extends BaseRecyclerViewAdapter<MomentsModel.DataBean>  {

    public MomentsModel model;
    public int current_card_id = 0;

    public static int CARD_MYSELF = -1;
    public static int CARD_FOLLOWED = 0;
    public static int CARD_NOT_FOLLOWED = 1;

    public CardAdapter(Context context, List<MomentsModel.DataBean> momentsModels, int itemLayoutId,MomentsModel model) {
        super(context, momentsModels, itemLayoutId);
        this.model=model;
    }

    public interface CardActionListener{
        void comment(MomentsModel.DataBean dataBean);
        void like(MomentsModel.DataBean dataBean);
        void detail(MomentsModel.DataBean dataBean);
        void dislike(MomentsModel.DataBean dataBean);
        void follow(MomentsModel.DataBean dataBean);
        void cancelFollow(MomentsModel.DataBean dataBean);
    }

    public CardActionListener actionListener;

    public void setOnActionListener(CardActionListener listener){
        this.actionListener=listener;
    }

    @Override
    protected void bind(BaseViewHolder holder, MomentsModel.DataBean dataBean) {
        holder.setText(R.id.txt_circle_name,dataBean.getNickname());
        holder.setImgUrl(R.id.img_circle_pic,dataBean.getPic_url());
        holder.setText(R.id.txt_circle_title,dataBean.getContent());

        QMUILinearLayout llayout_header_background = holder.getmItemView().findViewById(R.id.llayout_card_header_info);
        QMUIRadiusImageView imageView = holder.getmItemView().findViewById(R.id.img_circle_pic);

        Drawable drawable = mContext.getResources().getDrawable(R.drawable.shadow);
        drawable.setAlpha(20);
        llayout_header_background.setBackground(drawable);


        QMUIRoundButton btn_follow = (holder.getmItemView().findViewById(R.id.btn_circle_follow));

        // 默认是关注
        if (dataBean.isNotFollowed() == CARD_FOLLOWED){
            // 关注了
            btn_follow.setText("已关注");
        }
        else if (dataBean.isNotFollowed() == CARD_MYSELF){
            btn_follow.setVisibility(View.GONE);
        }

        View v = holder.getmItemView();
        if (actionListener!=null){

            v.findViewById(R.id.btn_circle_comment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionListener.comment(dataBean);
                }
            });

            v.findViewById(R.id.btn_circle_detail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionListener.detail(dataBean);
                }
            });

            v.findViewById(R.id.btn_circle_like).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionListener.like(dataBean);
                }
            });

            v.findViewById(R.id.btn_circle_dislike).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionListener.dislike(dataBean);
                }
            });

            v.findViewById(R.id.btn_circle_follow).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dataBean.isNotFollowed() == CARD_NOT_FOLLOWED){
                        //没有关注
                        btn_follow.setText("已关注");
                        dataBean.setNotFollowed(CARD_FOLLOWED);
                        actionListener.follow(dataBean);
                    }
                    else if (dataBean.isNotFollowed() == CARD_FOLLOWED){
                        // 取消关注
                        btn_follow.setText("关注");
                        dataBean.setNotFollowed(CARD_NOT_FOLLOWED);
                        actionListener.cancelFollow(dataBean);
                    }
                    else {
                        btn_follow.setVisibility(View.GONE);
                    }
                }
            });

        }

    }

}
