package com.example.myronlg.asyncscrollviewdemo;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by myron.lg on 2015/6/25.
 */
public class AsyncScrollView extends ScrollView {

    private boolean asyncScrolling;
    private View bgView;
    private View fgView;
    private float lastY = -1;
    private float startY = -1;
    private int bgOriginalScrollY;
    private Scroller scroller;
    private float bgDrawableHeight;
    private View anchorView;
    private View copyAnchorView;
    private int threshold;

    public enum SCROLL_MODE {SELF_SCROLL, SUPER_SCROLL}

    ;
    private SCROLL_MODE scrollMode = SCROLL_MODE.SUPER_SCROLL;

    public AsyncScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AsyncScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AsyncScrollView(Context context) {
        super(context);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        if (t > threshold){
//            copyAnchorView.setVisibility(VISIBLE);
//        } else {
//            copyAnchorView.setVisibility(INVISIBLE);
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        bgView = ((ViewGroup) getChildAt(0)).getChildAt(0);
        fgView = ((ViewGroup) getChildAt(0)).getChildAt(1);
        anchorView = findViewById(R.id.tv_anchor);
//        copyAnchorView = findViewById(R.id.tv_anchor_copy);
        threshold = anchorView.getTop();
        bgDrawableHeight = getBgDrawableHeight();
        float bgOriginalVisualHeight = ((ViewGroup) fgView).getChildAt(0).getMeasuredHeight();
        bgOriginalScrollY = (int) ((bgDrawableHeight - bgOriginalVisualHeight) / 2);
        bgView.scrollTo(0, bgOriginalScrollY);

//        View innerScrollView = ((ViewGroup) fgView).getChildAt(2);
//        int height = getMeasuredHeight() - anchorView.getMeasuredHeight();
//        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY, height);
//        innerScrollView.measure(widthMeasureSpec, newHeightMeasureSpec);
    }

    public void setCopyAnchorView(View copyAnchorView) {
        this.copyAnchorView = copyAnchorView;
    }

    private float getBgDrawableHeight() {
        ImageView bgImageView = (ImageView) bgView;
        Drawable bgDrawable = bgImageView.getDrawable();
//        float scaleRatio = bgImageView.getMeasuredWidth() / bgDrawable.getIntrinsicWidth();
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleRatio, scaleRatio);
//        bgImageView.setImageMatrix(matrix);
        return ((float) bgDrawable.getIntrinsicHeight()) * bgImageView.getMeasuredWidth() / bgDrawable.getIntrinsicWidth();
        // matrix is not ready now
//        Matrix matrix = bgImageView.getMatrix();
//        RectF rectF = new RectF(0, 0, bgDrawable.getIntrinsicWidth(), bgDrawable.getIntrinsicHeight());
//        matrix.mapRect(rectF);
//        float x = rectF.height();
//        float y = rectF.bottom - rectF.top;

//        return rectF.height();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (!asyncScrolling && !needAsyncScroll()) {
//            return super.dispatchTouchEvent(ev);
//        }
//        asyncScrolling = true;
//        handleTouchEvent(ev);


        return super.dispatchTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (getScrollY() > threshold){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        handleTouchEvent(ev);
        if (fgView.getScrollY() == 0){
            super.onTouchEvent(ev);
        }
        return true;

//        return super.onTouchEvent(ev);
    }

    private boolean handleTouchEvent(MotionEvent ev) {
        // why sometimes i can't get down event
        boolean interesting = false;
        final int action = ev.getAction();
        float y = ev.getY();
        if (lastY == -1) {
            lastY = y;
        }
        if (startY == -1){
            startY = y;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = y;
//                return super.dispatchTouchEvent(ev);
//            break;
            case MotionEvent.ACTION_MOVE:
//                float dy = y - lastY;
//                fgView.scrollBy(0, (int) (-dy*0.5F));
//                bgView.scrollBy(0, (int) (-dy*0.25F));

                float dy = y - startY;
//                if (fgView.getScrollY() <= 0
//                        || (getScrollY() == 0 && dy > 0)) {
                    if ((getScrollY() == 0 && dy > 0)) {
//                if (getScrollY() == 0 && dy > 0 && (y-lastY) > 0) {

                        // self scroll
                        fgView.scrollTo(0, Math.round(-dy * 0.5F + 0.5F));
                        if (fgView.getScrollY() <= -2 * bgOriginalScrollY) {
                            bgView.scrollTo(0, Math.round(-dy * 0.5F + 0.5F + 2 * bgOriginalScrollY));
                        } else {
                            bgView.scrollTo(0, Math.round(-dy * 0.25F + 0.5F + bgOriginalScrollY));
                        }

//                    if (scrollMode == SCROLL_MODE.SUPER_SCROLL){
//                        scrollMode = SCROLL_MODE.SELF_SCROLL;
//                        super.dispatchTouchEvent(generateCancelEvent(ev));
//                    }
                    } else {
//                    // super scroll
//                    if (scrollMode == SCROLL_MODE.SELF_SCROLL){
//                        scrollMode = SCROLL_MODE.SUPER_SCROLL;
//                        super.dispatchTouchEvent(ev);
//                    }
//                    return super.dispatchTouchEvent(ev);
                    }
//                }
//        }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (fgView.getScrollY() != 0) {
                    scroller.startScroll(0, fgView.getScrollY(), 0, -fgView.getScrollY());
                    invalidate();
                }
                lastY = -1;
                startY = -1;
//                return super.dispatchTouchEvent(ev);
//            break;
            default:
        }

        lastY = y;
        return false;
    }

    private MotionEvent generateCancelEvent(MotionEvent other) {
        MotionEvent cancelEvent = MotionEvent.obtain(other);
        cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
        return cancelEvent;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            fgView.scrollTo(scroller.getCurrX(), scroller.getCurrY());

            bgView.scrollTo(scroller.getCurrX(), Math.round(scroller.getCurrY() * 0.5F + bgOriginalScrollY));
            postInvalidate();
        } else {
            super.computeScroll();
        }
    }

    private boolean needAsyncScroll() {
        return getScrollY() == (getHeight() - getChildAt(0).getMeasuredHeight());
    }


}
