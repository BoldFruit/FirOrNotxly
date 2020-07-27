package com.neuqer.fitornot.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * @author YangCihang
 * @since 17/10/9.
 * email yangcihang@hrsoft.net
 */

public abstract class NoBarActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init();
    }
}
