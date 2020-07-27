package com.neuqer.fitornot.base.mvp.presenter;

import com.neuqer.fitornot.base.mvp.contract.BaseContract;

/**
 * @author YangCihang
 * @since 17/10/9.
 * email yangcihang@hrsoft.net
 */

public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {
    protected T mView;

    /**
     * P层构造方法;
     * 创建P层时就进行双向绑定
     * @param mView V层的引用
     */
    @SuppressWarnings("unchecked")
    public BasePresenter(T mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    /**
     * 销毁P层,销毁V与P之间的引用
     */
    @SuppressWarnings("unchecked")
    @Override
    public void destroy() {
        T view = mView;
        mView = null;
        if (view != null) {
            //销毁V层对P层的引用
            view.setPresenter(null);
        }
    }
}
