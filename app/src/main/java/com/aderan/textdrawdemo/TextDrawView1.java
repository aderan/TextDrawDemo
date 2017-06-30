package com.aderan.textdrawdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by fenglibin on 01/04/2017.
 */

public class TextDrawView1 extends View {

    public static final String TEXT = "鸟儿已经飞过";
    public static final String TEXT2 = "天空不留痕迹";
    public static final String TEXT3 = "向着太阳";

    private TextPaint mTextPaint;
    private LinearGradient mLinearGradient;

    public TextDrawView1(Context context) {
        super(context);

        init();
    }

    public TextDrawView1(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public TextDrawView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mTextPaint = new TextPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.rotate(5, getWidth() / 2, getHeight() / 2);
        mTextPaint.setTextSize(200);
        mTextPaint.clearShadowLayer();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth(20);

        // 计算Baseline绘制的起点X轴坐标 ，计算方式：画布宽度的一半 - 文字宽度的一半
        int baseX = (int) (canvas.getWidth() / 2 - mTextPaint.measureText(TEXT) / 2);

        // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
        int baseY = (int) ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2) - 150);

        mLinearGradient = new LinearGradient(0, baseY, 0, baseY + ((mTextPaint.descent() + mTextPaint.ascent()) / 2),
                Color.parseColor("#C1FCF2"), Color.WHITE, Shader.TileMode.CLAMP);
        //mLinearGradient = new LinearGradient(baseX, baseY, baseX + mTextPaint.measureText(TEXT), baseY + ((mTextPaint.descent() + mTextPaint.ascent()) / 2),
        //        Color.RED, Color.YELLOW, Shader.TileMode.CLAMP);
        mTextPaint.setShader(mLinearGradient);

        // 居中画一个文字
        canvas.drawText(TEXT, baseX, baseY, mTextPaint);

        mTextPaint.setTextSize(200);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setShader(null);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStrokeWidth(3);

        // 计算Baseline绘制的起点X轴坐标 ，计算方式：画布宽度的一半 - 文字宽度的一半
        baseX = (int) (canvas.getWidth() / 2 - mTextPaint.measureText(TEXT) / 2);

        // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
        baseY = (int) ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2) - 150);

        // 居中画一个文字
        canvas.drawText(TEXT, baseX, baseY, mTextPaint);

        // Draw Line Two
        mTextPaint.setTextSize(200);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth(20);

        // 计算Baseline绘制的起点X轴坐标 ，计算方式：画布宽度的一半 - 文字宽度的一半
        baseX = (int) (canvas.getWidth() / 2 - mTextPaint.measureText(TEXT2) / 2);

        // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
        baseY = (int) ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2) + 150);

        mLinearGradient = new LinearGradient(0, baseY, 0, baseY + ((mTextPaint.descent() + mTextPaint.ascent()) / 2),
                Color.WHITE, Color.parseColor("#F6C7C9"), Shader.TileMode.CLAMP);
        //mLinearGradient = new LinearGradient(baseX, baseY, baseX + mTextPaint.measureText(TEXT), baseY + ((mTextPaint.descent() + mTextPaint.ascent()) / 2),
        //        Color.RED, Color.YELLOW, Shader.TileMode.CLAMP);
        mTextPaint.setShader(mLinearGradient);

        // 居中画一个文字
        canvas.drawText(TEXT2, baseX, baseY, mTextPaint);

        mTextPaint.setTextSize(200);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setShader(null);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setShadowLayer(10, 5, 5, Color.BLUE);

        // 计算Baseline绘制的起点X轴坐标 ，计算方式：画布宽度的一半 - 文字宽度的一半
        baseX = (int) (canvas.getWidth() / 2 - mTextPaint.measureText(TEXT2) / 2);

        // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
        baseY = (int) ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2) + 150);

        // 居中画一个文字
        canvas.drawText(TEXT2, baseX, baseY, mTextPaint);

        Log.e("Aderan", "Width " + mTextPaint.measureText(TEXT3));
        Rect bound = new Rect();
        mTextPaint.getTextBounds(TEXT3, 0, TEXT3.length() - 1, bound);
        Log.e("Aderan", "Bound " + bound.toString());

        StaticLayout layout = new StaticLayout(TEXT3, mTextPaint, 100, Layout.Alignment.ALIGN_CENTER, 0.8f, 0, false);
        layout.draw(canvas);

        canvas.restore();
    }
}
