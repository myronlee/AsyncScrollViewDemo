package com.example.myronlg.asyncscrollviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by myron.lg on 2015/6/25.
 */
public class TestOnInterceptViewGroup extends FrameLayout {
    public TestOnInterceptViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TestOnInterceptViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestOnInterceptViewGroup(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("", ev.getAction()+"");
        return super.onInterceptTouchEvent(ev);
    }
}
