package com.aderan.textdrawdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by fenglibin on 12/04/2017.
 */

public class CanvasDrawHelper {
    public static boolean DEBUG = true;

    private Paint mRectPaint;
    private int mColorRect = Color.parseColor("#123456");
    private int mStrokeWidthRect = 4;

    private Paint mLinePaint;
    private int mColorLine = Color.parseColor("#654321");
    private int mStrokeWidthLine = 2;

    public CanvasDrawHelper() {
        updatePaint();
    }

    private void updatePaint() {
        if (mRectPaint == null)
            mRectPaint = new Paint();
        mRectPaint.setStrokeWidth(mStrokeWidthRect);
        mRectPaint.setColor(mColorRect);
        mRectPaint.setStyle(Paint.Style.STROKE);

        if (mLinePaint == null)
            mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(mStrokeWidthLine);
        mLinePaint.setColor(mColorLine);
        mLinePaint.setStyle(Paint.Style.FILL);
    }

    public void drawRect(Canvas canvas, Rect rect) {
        if (DEBUG)
            canvas.drawRect(rect, mRectPaint);
    }

    public void drawLineX(Canvas canvas, int x) {
        if (DEBUG)
            canvas.drawLine(x, 0, x, DisplayUtil.getScreenHeight(), mLinePaint);
    }

    public void drawLineY(Canvas canvas, int y) {
        if (DEBUG)
            canvas.drawLine(0, y, DisplayUtil.getScreenWidth(), y, mLinePaint);
    }
}
