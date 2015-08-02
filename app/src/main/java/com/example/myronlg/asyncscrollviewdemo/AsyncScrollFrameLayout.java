package com.example.myronlg.asyncscrollviewdemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by myron.lg on 2015/6/25.
 */
public class AsyncScrollFrameLayout extends FrameLayout {


    private View bgView;
    private View fgView;
    private ViewPager viewPager;

    private float lastY = -1;
    private Scroller scroller;

    /**
     * A value that indicate who should scroll, this frameLayout or inner scrollable view.
     * If the inner scrollable view is entirely visible, let it scroll. Otherwise, let this frameLayout scroll.
     */
    private int interceptThreshold;
    private boolean intercept;
    private int bgDrawableHiddenHeight;

    public AsyncScrollFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AsyncScrollFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AsyncScrollFrameLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        bgView = getChildAt(0);
        fgView = getChildAt(1);

        View userInfoView = ((ViewGroup) fgView).getChildAt(0);
        View anchorView = ((ViewGroup) fgView).getChildAt(1);
        viewPager = (ViewPager) ((ViewGroup) fgView).getChildAt(2);

        interceptThreshold = anchorView.getTop();

        float bgDrawableHeight = getBgDrawableHeight();
        float bgDrawableVisibleHeight = userInfoView.getMeasuredHeight();
        bgDrawableHiddenHeight = (int) (bgDrawableHeight - bgDrawableVisibleHeight);

        FrameLayout.LayoutParams bgViewLayoutParams = (LayoutParams) bgView.getLayoutParams();
        bgViewLayoutParams.setMargins(0, -bgDrawableHiddenHeight/2, 0, 0);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
        layoutParams.height = getMeasuredHeight() - anchorView.getMeasuredHeight();
        viewPager.setLayoutParams(layoutParams);
    }

    private float getBgDrawableHeight() {
        ImageView bgImageView = (ImageView) bgView;
        Drawable bgDrawable = bgImageView.getDrawable();
        return ((float) bgDrawable.getIntrinsicHeight()) * bgImageView.getMeasuredWidth() / bgDrawable.getIntrinsicWidth();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return handleTouchEvent(ev);
    }

/*
    不要去重写这两个函数，想要完全掌控触摸事件的传递和处理你必须要重写dispatchTouchEvent()，而重写dispatchTouchEvent就足够完全掌控触摸事件的传递和处理
    因为这里涉及一个手势的前半部分由一个控件处理，后半部分由另一个控件处理的情况，Android事件处理框架是把一个手势交给一个控件处理的。
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (getScrollY() < interceptThreshold) {
            outerScrolling = true;
            return true;
        } else {
            if (outerScrolling) {
                dispathDownEventMannualy();
            }
        }
        return super.onInterceptTouchEvent(ev);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
*/

    private boolean handleTouchEvent(MotionEvent ev) {
        if (lastY == -1) {
            lastY = ev.getY();
        }
        boolean flag = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flag = onDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                flag = onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                flag = onEnd(ev);
                break;
            default:
        }
        lastY = ev.getY();
        return flag;
    }

    private boolean onDown(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return true;
    }

    private boolean onMove(MotionEvent ev) {
        float dy = ev.getY() - lastY;

        //this container take care of scrolling
        if (fgView.getScrollY() < interceptThreshold) {

            // pull down over scroll
            if (fgView.getScrollY() < 0 || (fgView.getScrollY() == 0 && dy > 0)) {
                if (fgView.getScrollY() <= -bgDrawableHiddenHeight) {//if the bgView is shown entirely, sync scroll
                    scrollBy(0, computeDy(-dy * 0.5F));
                } else {//if the bgView is not shown entirely, async scroll
                    fgView.scrollBy(0, computeDy(-dy * 0.5F));
                    bgView.scrollBy(0, computeDy(-dy * 0.25F));
                }
            } else {//pull up
                int scrollYDelta = computeDy(-dy);
                if (fgView.getScrollY()+scrollYDelta < 0){
                    // let async scroll take care from here
                    scrollBy(0, -fgView.getScrollY());
                } else if (fgView.getScrollY()+scrollYDelta >= interceptThreshold) {
                    // let bottom scrollable view take care from here
                    scrollBy(0, interceptThreshold -fgView.getScrollY());
                } else {
                    scrollBy(0, scrollYDelta);
                }
            }
            intercept = true;
            return true;
        } else {
           if (isViewPagerReachTop() && dy > 0){
               if (!intercept) {
                   fgView.setScrollY(interceptThreshold);
                   bgView.setScrollY(interceptThreshold);
                   dispatchCancelEvent(ev);
               }
               int scrollYDelta = computeDy(-dy);
               scrollBy(0, scrollYDelta);
//               this is 99% impossible
//               if (fgView.getScrollY()+scrollYDelta < 0){
//                   scrollBy(0, -fgView.getScrollY());
//               } else {
//                   scrollBy(0, scrollYDelta);
//               }
               intercept = true;
               return true;
           } else {
               if (intercept){
                   dispatchDownEvent(ev);
               }
               intercept = false;
               return super.dispatchTouchEvent(ev);
           }
        }
    }

    private void dispatchCancelEvent(MotionEvent ev){
        MotionEvent cancelEvent = MotionEvent.obtain(ev);
        cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
        super.dispatchTouchEvent(cancelEvent);
    }

    private void dispatchDownEvent(MotionEvent ev){
        MotionEvent downEvent = MotionEvent.obtain(ev);
        downEvent.setAction(MotionEvent.ACTION_DOWN);
        super.dispatchTouchEvent(downEvent);
    }
    private boolean onEnd(MotionEvent ev) {
        if (fgView.getScrollY() <= 0) {
            scroller.startScroll(bgView.getScrollY(), fgView.getScrollY(), -bgView.getScrollY(), -fgView.getScrollY());
            invalidate();
        }
        lastY = -1;
        intercept = false;
        return super.dispatchTouchEvent(ev);
    }

    private boolean isViewPagerReachTop() {
        int currentIndex = viewPager.getCurrentItem();
        View currentPage = viewPager.findViewWithTag(currentIndex);
        return isViewReachTop(currentPage);
    }

    private boolean isViewReachTop(View view) {
        if (view instanceof AdapterView<?>) {
            AdapterView<?> adapterView = (AdapterView<?>) view;
            if (adapterView.getLastVisiblePosition() == 0
                    && adapterView.getChildAt(0).getTop() >= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            if (view.getScrollY() <= 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void scrollBy(int x, int y) {
        fgView.scrollBy(x, y);
        bgView.scrollBy(x, y);
    }

    private int computeDy(float dy){
        if (dy > 0){
            return Math.round(dy+0.5F);
        } else {
            return Math.round(dy-0.5F);
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            fgView.scrollTo(0, scroller.getCurrY());
            bgView.scrollTo(0, scroller.getCurrX());
            postInvalidate();
        } else {
            super.computeScroll();
        }
    }
}
