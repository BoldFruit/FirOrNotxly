package com.neuqer.fitornot.business.fittingroom.contract;

import com.neuqer.fitornot.base.mvp.contract.BaseContract;
import com.neuqer.fitornot.business.clothes.modle.response.GetClothesRspModle;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/9/28
 * email 798382030@qq.com
 */

public interface CollocationActivityContract {

    interface Presenter extends BaseContract.Presenter {
        void onReqGetClothesByWord(int type);
    }

    interface View extends BaseContract.View<Presenter> {
        void onChangeData(List<GetClothesRspModle.DataEntity> mDataEntities, int type);

        void dismissDialog();
    }
}
