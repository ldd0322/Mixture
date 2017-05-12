package com.ldd.mixture;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by S01 on 2017/5/5.
 */

public class NoSlidingViewPaper extends ViewPager {
    public NoSlidingViewPaper(Context context) {
        super(context);
    }

    public NoSlidingViewPaper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /*
        * 表示把滑动事件传递给下一个view
        */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
    /*
     * 可以啥都不做
     */
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }
}
