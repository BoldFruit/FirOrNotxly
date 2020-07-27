package com.neuqer.fitornot.business.fittingroom.presenter;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.base.mvp.presenter.BasePresenter;
import com.neuqer.fitornot.business.clothes.modle.request.GetClothesByWordReqModle;
import com.neuqer.fitornot.business.clothes.modle.response.GetClothesRspModle;
import com.neuqer.fitornot.business.fittingroom.contract.CollocationActivityContract;
import com.neuqer.fitornot.business.fittingroom.model.FittingRoomHelper;
import com.neuqer.fitornot.business.fittingroom.view.activity.CollocationActivity;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.LogUtil;

/**
 * @author DuLong
 * @since 2019/9/28
 * email 798382030@qq.com
 */
public class CollocationActivityPresenter extends BasePresenter<CollocationActivityContract.View> implements CollocationActivityContract.Presenter{

    private String[] mType = new String[] {"上衣", "裤子", "裙装", "鞋袜", "配饰", "包包"};
    /**
     * P层构造方法;
     * 创建P层时就进行双向绑定
     *
     * @param mView V层的引用
     */
    public CollocationActivityPresenter(CollocationActivityContract.View mView) {
        super(mView);
    }

    @Override
    public void onReqGetClothesByWord(int type) {
        GetClothesByWordReqModle mData = new GetClothesByWordReqModle();
        mData.setWord(mType[type]);
        FittingRoomHelper.getClothesByWord(mData, new IDataCallBack.Callback<ApiResponse<GetClothesRspModle>>() {
            @Override
            public void onFailedLoaded(String error) {
                LogUtil.e("通过词语获取衣物", "失败" + error);
            }

            @Override
            public void onDataLoaded(ApiResponse<GetClothesRspModle> mGetClothesRspModleApiResponse) {
                mView.onChangeData(mGetClothesRspModleApiResponse.getData().getData(), type);
                LogUtil.e("通过词语获取衣物", "成功");
            }
        });
    }
}
