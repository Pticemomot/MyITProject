package com.vilorg.mydraw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class PaintView extends View {
    public static final int PAINT_SIZE = 10; //размер кисти
    public static int DEFAULT_COLOR = Color.BLACK; //дефолтный цвет для рисования
    public static int DEFAULT_BG_COLOR = Color.WHITE; //цвет для заднего фона
    private static final float TOUCH_TOLERANCE = 0;
    private float mX, mY;
    private Path mPath;
    private Paint mPaint;
    public static ArrayList<FingerPath> paths = new ArrayList<>();
    public static int currentColor; //текущий цвет
    public static int backgroundColor = DEFAULT_BG_COLOR; //иницилизация цвета заднего фона
    private int currentPaintSize; //текущий размер кисти
    private Bitmap mBitmap; //изображение для холста, на которое наносим рисунок
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void init(DisplayMetrics metrics) {
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        currentPaintSize = PAINT_SIZE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        mCanvas.drawColor(backgroundColor);

        for (FingerPath fp : paths) {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.currentPaintSize);
            mPaint.setMaskFilter(null);

            mCanvas.drawPath(fp.path, mPaint);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y){
        mPath = new Path();
        FingerPath fp = new FingerPath(currentColor, currentPaintSize, mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                touchUp();
                invalidate();
                break;
        }

        return true;
    }

    public void clearAll() {
        backgroundColor = DEFAULT_BG_COLOR;
        paths.clear();
        invalidate();
    }
}
