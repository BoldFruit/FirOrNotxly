package com.neuqer.fitornot.base.mvp;

public interface IDataCallBack {
    /**
     * 数据加载失败和成功都关注
     *
     * @param <T> 返回数据类型
     */
    interface Callback<T> extends SuccessCallback<T>, FailedCallback{

    }

    /**
     * 只关注数据加载成功的接口
     *
     * @param <T>
     */
    interface SuccessCallback<T> extends INotifyListener{
        void onDataLoaded(T t);
    }

    /**
     * 只关注数据加载失败的接口
     */
    interface FailedCallback extends INotifyListener{
        void onFailedLoaded(String error);
    }


}
