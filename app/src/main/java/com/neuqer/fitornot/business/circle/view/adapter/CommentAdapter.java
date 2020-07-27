package com.neuqer.fitornot.business.circle.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.base.adapter.BaseViewHolder;
import com.neuqer.fitornot.business.circle.model.response.CommentsModel;

import java.util.List;

public class CommentAdapter extends BaseRecyclerViewAdapter<CommentsModel> {


    public CommentAdapter(Context context, List<CommentsModel> commentsModels, int itemLayoutId) {
        super(context, commentsModels, itemLayoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, CommentsModel commentsModel) {
        holder.setText(R.id.txt_comment_avatar,commentsModel.getFromName());
        holder.setText(R.id.txt_comment_time,commentsModel.getCreated_at());
        holder.setText(R.id.txt_comment_words,commentsModel.getContent());
        holder.setImgUrl(R.id.img_comment_avatar,commentsModel.getAvatar_url());
    }
}
