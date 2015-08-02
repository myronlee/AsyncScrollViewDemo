package com.example.myronlg.asyncscrollviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by myron.lg on 2015/7/2.
 */
public class TestEventRouteLinearLayout extends LinearLayout {
    public TestEventRouteLinearLayout(Context context) {
        super(context);
    }

    public TestEventRouteLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestEventRouteLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("onTouchEvent", event.getAction()+"");
        return super.onTouchEvent(event);
    }
}
