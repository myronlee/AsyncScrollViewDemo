package com.example.myronlg.asyncscrollviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by myron.lg on 2015/6/24.
 */
public class CircleImageView extends ImageView {

    private float cx;
    private float cy;
    private float radius;
    private Paint paint;
    private Bitmap srcBitmap;
    private Bitmap circleBitmap;
    private Paint paint2;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawColor(Color.CYAN);

//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
//        paint.setColor(Color.LTGRAY);
//        canvas.drawBitmap(srcBitmap, 0, 0, paint);
//        paint.setColor(Color.CYAN);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        canvas.drawCircle(cx, cy, radius, paint);
        canvas.drawBitmap(circleBitmap, 0, 0, paint2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    private void init() {
        final float width = getMeasuredWidth();
        final float height = getMeasuredHeight();
        final float size = Math.min(width, height);
        cx = size * 0.5F;
        cy = size * 0.5F;
        radius = size * 0.5F;
        paint = new Paint();

        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.potrait);

        circleBitmap = Bitmap.createBitmap(((int) size), ((int) size), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(circleBitmap);

        canvas.drawCircle(cx, cy, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap, 0, 0, paint);

//        setImageBitmap(circleBitmap);
//        getDrawable().draw(canvas);
        paint2 = new Paint();

    }
}
