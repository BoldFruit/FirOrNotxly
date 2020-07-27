package com.neuqer.fitornot.business.fittingroom.contract;

import android.content.Context;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.base.mvp.contract.BaseContract;
import com.neuqer.fitornot.business.fittingroom.model.request.SetClothesModel;
import com.neuqer.fitornot.business.fittingroom.model.response.GetClothesRspModel;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.tencent.tauth.IUiListener;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/8/17
 * email 798382030@qq.com
 */

public interface FittingContract {

    interface Presenter extends BaseContract.Presenter {

        void saveClothes(List<SetClothesModel.ClothesEntity> clothes, boolean isRequestSave);

        void shareMyClothes(Context context, android.view.View view, IUiListener listener);

        void requestGetClothes();


    }

    interface View extends BaseContract.View<Presenter> {
        void checkIsAdded();

    }

    interface Model extends BaseContract.Model {
        void saveClothes(List<SetClothesModel.ClothesEntity> clothes, IDataCallBack.Callback<ApiResponse<Void>> callBack);

        void getClothes(IDataCallBack.Callback<GetClothesRspModel> callback);
    }
}
