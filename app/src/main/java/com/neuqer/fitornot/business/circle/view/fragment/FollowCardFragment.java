package com.neuqer.fitornot.business.circle.view.fragment;

import android.content.Context;
import android.view.View;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.fragment.BaseFragment;
import com.neuqer.fitornot.business.circle.contract.CircleContract;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.business.circle.presenter.FollowingCircleFragmentPresenter;
import com.neuqer.fitornot.business.circle.view.adapter.CardAdapter;
import com.neuqer.fitornot.util.ToastUtil;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import butterknife.BindView;

/**
 * Author: Kingtous
 * Since: 2019-09-26
 * Email: me@kingtous.cn
 */
public class FollowCardFragment extends ALLCardFragment {

    @Override
    protected void loadData() {
        mPresenter.getAllFollowingMoments(1);
    }

    @Override
    protected CircleContract.Presenter initPresenter() {
        return new FollowingCircleFragmentPresenter(this);
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
                        mPresenter.getAllFollowingMoments(nextPage);
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
}
