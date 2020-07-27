package com.neuqer.fitornot.business.circle.view.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Author: Kingtous
 * Since: 2019-09-27
 * Email: me@kingtous.cn
 */
public class NonScrollViewPager extends ViewPager {
    public NonScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonScrollViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
}
