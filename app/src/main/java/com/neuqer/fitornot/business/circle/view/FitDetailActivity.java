package com.neuqer.fitornot.business.circle.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.BasePresenterActivity;
import com.neuqer.fitornot.business.circle.contract.CircleContract;
import com.neuqer.fitornot.business.circle.model.request.CommentRequestModel;
import com.neuqer.fitornot.business.circle.model.response.CommentsModel;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.business.circle.presenter.CircleFragmentPresenter;
import com.neuqer.fitornot.business.circle.presenter.FitDetailActivityPresenter;
import com.neuqer.fitornot.business.circle.view.adapter.CommentAdapter;
import com.neuqer.fitornot.util.ToastUtil;
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomAreaBehavior;
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomRecyclerView;
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedScrollLayout;
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedTopAreaBehavior;
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedTopLinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: Kingtous
 * Since: 2019-10-03
 * Email: me@kingtous.cn
 */
public class FitDetailActivity extends BasePresenterActivity<CircleContract.Presenter> implements CircleContract.View,QMUIRoundButton.OnClickListener {

    public static final String DETAIL_CODE = "DETAIL";

    MomentsModel.DataBean dataBean;
    @BindView(R.id.slayout_nested)
    QMUIContinuousNestedScrollLayout slayoutNested;

    QMUIContinuousNestedTopLinearLayout layout_main;
    QMUIContinuousNestedBottomRecyclerView layout_comment;
    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;
    @BindView(R.id.qprlayout_refresh)
    QMUIPullRefreshLayout qprlayoutRefresh;


    // MAIN
    CommentAdapter cAdapter;
    private Handler mHandler;
    TextView circleTitle ;
    TextView browseNumber ;
    TextView commentNumber ;
    TextView content ;
    TextView likeNumber;
    ImageView pic;
    QMUIFloatLayout floatLayout;
    QMUIRadiusImageView pic_avatar;
    QMUIRoundButton btn_comment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fit_detail;
    }

    @Override
    protected void initVariable() {
        initTopBar("详情");
        QMUIStatusBarHelper.translucent(this);
        mHandler = new Handler();
        // 还没有接口，等待
        dataBean = new Gson().fromJson(getIntent().getStringExtra(DETAIL_CODE), MomentsModel.DataBean.class);
        if (dataBean == null) {
            ToastUtil.showToast("未获取到数据");
            finish();
        }
    }

    public void processData(List<CommentsModel> models){
        qprlayoutRefresh.finishRefresh();
        if (cAdapter != null){
            cAdapter.clear();
        }
        commentNumber.setText(String.valueOf(models.size()));
        cAdapter = new CommentAdapter(this,models,R.layout.item_comment);
        layout_comment.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();
    }

    public void reloadComments(){
        loadData();
    }

    @Override
    protected void initView() {

        qprlayoutRefresh.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                loadData();
            }
        });
        // 初始化main
        layout_main = (QMUIContinuousNestedTopLinearLayout) LayoutInflater.from(this).inflate(R.layout.view_fit_detail_main, null);
        circleTitle = layout_main.findViewById(R.id.txt_detail_title);
        browseNumber = layout_main.findViewById(R.id.txt_detail_browse_number);
        commentNumber = layout_main.findViewById(R.id.txt_detail_comment_number);
        content = layout_main.findViewById(R.id.txt_detail_content);
        likeNumber = layout_main.findViewById(R.id.txt_detail_like_number);
        pic = layout_main.findViewById(R.id.img_detail_pic);
        pic_avatar = layout_main.findViewById(R.id.img_detail_avatar);
        btn_comment = layout_main.findViewById(R.id.btn_circle_comment);
        btn_comment.setOnClickListener(this);
        floatLayout=layout_main.findViewById(R.id.qflayout_detail_labels);
        browseNumber.setText(String.valueOf(dataBean.getViews_num()));
        likeNumber.setText(String.valueOf(dataBean.getLikes_num()));
        circleTitle.setText(dataBean.getContent());
        content.setText(dataBean.getContent());
        commentNumber.setText(String.valueOf(dataBean.getComments_num()));
        try {
            Glide.with(this).load(dataBean.getAvatar_url()).into(pic_avatar);
        }catch (NullPointerException e){
            
        }
        try {
            Glide.with(this).load(dataBean.getPic_url()).into(pic);
        }catch (NullPointerException e){

        }
        // 初始化标签
        List<MomentsModel.DataBean.TagsBean> tBeans = dataBean.getTags();
        for (MomentsModel.DataBean.TagsBean bean : tBeans){
            View v = LayoutInflater.from(this).inflate(R.layout.item_label,null);
            String tagName = bean.getTag_name();
            ((TextView)v.findViewById(R.id.label)).setText(tagName);
            floatLayout.addView(v);
        }


   commentNumber.setText(String.valueOf(3));
        layout_comment = (QMUIContinuousNestedBottomRecyclerView) LayoutInflater.from(this).inflate(R.layout.view_fit_detail_comment, null);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        layout_comment.setLayoutManager(linearLayoutManager);
        // 添加
        slayoutNested.setTopAreaView(layout_main, null);
        slayoutNested.setBottomAreaView(layout_comment, null);
        slayoutNested.setDraggableScrollBarEnabled(true);
    }

    @Override
    protected void loadData() {
        ((FitDetailActivityPresenter)mPresenter).getComment(dataBean.getId());
    }

    @Override
    protected CircleContract.Presenter initPresenter() {
        return new FitDetailActivityPresenter(this);
    }

    @Override
    public void onClick(View view) {
        QMUIBottomSheet sheet = new QMUIBottomSheet(this);
        sheet.setContentView(R.layout.item_input_detail);
        sheet.show();

        // 输入框
        EditText inputText = sheet.getContentView().findViewById(R.id.edit_detail);
        QMUIRoundButton btn_ok  = sheet.getContentView().findViewById(R.id.btn_pulish_request);
        btn_ok.setText("发布");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentRequestModel model=new CommentRequestModel();
                CommentRequestModel.CommentBean commentsBean =new CommentRequestModel.CommentBean();
                commentsBean.setTo(String.valueOf(dataBean.getId()));
                commentsBean.setContent(inputText.getText().toString());
                model.setComment(commentsBean);
                mPresenter.createComment(model);
                sheet.dismiss();
            }
        });
    }
}
