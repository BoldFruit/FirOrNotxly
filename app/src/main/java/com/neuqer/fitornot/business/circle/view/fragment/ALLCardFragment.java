package com.neuqer.fitornot.business.circle.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.fragment.BaseFragment;
import com.neuqer.fitornot.base.fragment.BasePresenterFragment;
import com.neuqer.fitornot.business.circle.contract.CircleContract;
import com.neuqer.fitornot.business.circle.model.request.CommentRequestModel;
import com.neuqer.fitornot.business.circle.model.response.CommentsModel;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.business.circle.presenter.ALLCircleFragmentPresenter;
import com.neuqer.fitornot.business.circle.presenter.CircleFragmentPresenter;
import com.neuqer.fitornot.business.circle.view.FitDetailActivity;
import com.neuqer.fitornot.business.circle.view.adapter.CardAdapter;
import com.neuqer.fitornot.util.ToastUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Author: Kingtous
 * Since: 2019-09-26
 * Email: me@kingtous.cn
 */
public class ALLCardFragment extends BasePresenterFragment<CircleContract.Presenter> implements CircleContract.View, CardAdapter.CardActionListener {
    @BindView(R.id.csv_fit_card)
    CardStackView csvFitCard;
    protected CardStackLayoutManager cardStackLayoutManager;
    protected MomentsModel tmpModel;
    protected CardAdapter cAdapter;
    protected Context mContext;
    protected CardStackListener cListener;
    protected int totalCardsNum = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_swipe_card;
    }

    @Override
    protected void initVariable() {
        mContext = getContext();
        cListener =new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {

            }

            @Override
            public void onCardSwiped(Direction direction) {
                // 给adapter + 1
                if (cAdapter.current_card_id + 1 >= totalCardsNum){
                    // 没了，查看是否有下一页，有则刷新，否则回到最初
                    if (tmpModel.getNext_page_url() != null){
                        int nextPage = tmpModel.getCurrent_page() + 1;
                        ((ALLCircleFragmentPresenter)mPresenter).getALLMoments(nextPage);
                    }
                    else {
                        loadData();
                    }
                }
                else {
                    cAdapter.current_card_id += 1;
                }

            }

            @Override
            public void onCardRewound() {

            }

            @Override
            public void onCardCanceled() {

            }

            @Override
            public void onCardAppeared(View view, int position) {

            }

            @Override
            public void onCardDisappeared(View view, int position) {

            }
        };

    }

    public void processData(MomentsModel models){
        if (cAdapter!=null){
            cAdapter.clear();
        }
        tmpModel = models;
        // 初始化
        totalCardsNum = models.getData().size();
        cAdapter = new CardAdapter(mContext,models.getData(),R.layout.item_card,models);
        cAdapter.setOnActionListener(this);
        csvFitCard.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        cardStackLayoutManager = new CardStackLayoutManager(mContext,cListener);
        RewindAnimationSetting setting = new RewindAnimationSetting.Builder().setInterpolator(new DecelerateInterpolator()).build();
        cardStackLayoutManager.setRewindAnimationSetting(setting);
        cardStackLayoutManager.setStackFrom(StackFrom.Bottom);
        //底部层叠
        cardStackLayoutManager.setVisibleCount(3);
        cardStackLayoutManager.setTranslationInterval(8);
        csvFitCard.setLayoutManager(cardStackLayoutManager);
    }

    @Override
    protected void loadData() {
        ((ALLCircleFragmentPresenter)mPresenter).getALLMoments(1);
    }

    @Override
    protected CircleContract.Presenter initPresenter() {
        return new ALLCircleFragmentPresenter(this);
    }

    @Override
    public void comment(MomentsModel.DataBean dataBean) {
        CommentRequestModel model = new CommentRequestModel();
        CommentRequestModel.CommentBean commentsBean = new CommentRequestModel.CommentBean();
        commentsBean.setTo(String.valueOf(dataBean.getId()));
        // 显示评论框
        QMUIBottomSheet sheet = new QMUIBottomSheet(mContext);
        sheet.setContentView(R.layout.item_input_detail);
        sheet.show();

        // 输入框
        EditText inputText = sheet.getContentView().findViewById(R.id.edit_detail);
        QMUIRoundButton btn_ok  = sheet.getContentView().findViewById(R.id.btn_pulish_request);
        btn_ok.setText("发布");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsBean.setContent(inputText.getText().toString());
                model.setComment(commentsBean);
                mPresenter.createComment(model);
                sheet.dismiss();
            }
        });
    }

    @Override
    public void like(MomentsModel.DataBean dataBean) {
        mPresenter.createLike(dataBean);
    }

    @Override
    public void detail(MomentsModel.DataBean dataBean) {
        String objectStr = new Gson().toJson(dataBean);
        Intent intent = new Intent(mContext, FitDetailActivity.class);
        intent.putExtra(FitDetailActivity.DETAIL_CODE,objectStr);
        startActivity(intent);
    }

    @Override
    public void dislike(MomentsModel.DataBean dataBean) {
        mPresenter.deleteLike(dataBean);
    }

    @Override
    public void follow(MomentsModel.DataBean dataBean) {
        mPresenter.createFollow(dataBean);
    }

    @Override
    public void cancelFollow(MomentsModel.DataBean dataBean) {
        mPresenter.deleteFollow(dataBean);
    }


}
