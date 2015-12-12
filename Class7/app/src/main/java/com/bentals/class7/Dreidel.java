package com.bentals.class7;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.Random;

/**
 * Created by Ben Tal's on 05/12/2015.
 */
public class Dreidel extends View {

    public final String TAG = getClass().getSimpleName();

    private Paint paint;
    private float top, right, width, height;

    private Path path;
    private Random random;

    public Dreidel(Context context) {
        super(context);
        init(null, 0);
    }

    public Dreidel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public Dreidel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        path = new Path();

        random = new Random(System.currentTimeMillis());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        right = getPaddingRight();
        top = getPaddingTop();
        width = w - (getPaddingLeft() + getPaddingRight());
        height = h - (getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw with lines
//        canvas.drawLine(right + width / 2, top, right + width / 2, top + height / 4, paint);
//        canvas.drawLine(right, top + height/4, right + width, top + height/4, paint);
//        canvas.drawLine(right, top + height/4, right + width/2, top + height, paint);
//        canvas.drawLine(right + width, top + height/4, right + width/2, top + height, paint);


        //draw with path
        path.moveTo(right + width / 2, top);
        path.lineTo(right + width / 2, top + height / 4);

        path.moveTo(right, top + height / 4);
        path.lineTo(right + width, top + height / 4);
        path.lineTo(right + width / 2, top + height);
        path.close();

        canvas.drawPath(path, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "actionDown");
                rotate();
                //changeColor();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "actionMove");

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "actionUp");
                break;
        }
        return true;
    }

    private void rotate() {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(this, "rotationY", 0f, 180f);
        rotationAnimator.setDuration(400);
        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotationAnimator.setInterpolator(new LinearInterpolator());
        rotationAnimator.start();
        rotationAnimator.cancel();

    }
    private void changeColor(){
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        paint.setColor(Color.rgb(r, g, b));
        invalidate();

    }
}
