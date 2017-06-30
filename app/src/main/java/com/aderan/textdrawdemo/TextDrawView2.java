package com.aderan.textdrawdemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.NinePatchDrawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by fenglibin on 01/04/2017.
 */

public class TextDrawView2 extends View {

    public static final String TEXT = "鸟儿已经飞过";
    public static final String TEXT2 = "天空不留痕迹";
    public static final String TEXT3 = "鸟儿已经飞过天空不留痕迹";
    public static final String TEXT4 = "鸟儿已经飞过ABC天空不留痕迹";

    private TextPaint mTextPaint;
    private LinearGradient mLinearGradient;

    private Paint mTestRectPaint;
    private Paint mTestLinePaint;
    private Typeface mTypeface;
    private CanvasDrawHelper mCanvasDrawHelper;

    public TextDrawView2(Context context) {
        super(context);

        init();
    }

    public TextDrawView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public TextDrawView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mCanvasDrawHelper = new CanvasDrawHelper();

        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "simfang.ttf");
        mTypeface = Typeface.create(typeface, Typeface.BOLD);
        mTextPaint = new TextPaint();
        mTextPaint.setTypeface(mTypeface);

        mTestRectPaint = new Paint();
        mTestRectPaint.setStrokeWidth(1);
        mTestRectPaint.setColor(Color.parseColor("#123456"));
        mTestLinePaint = new Paint();
        PathEffect effect = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
        mTestLinePaint.setAntiAlias(true);
        mTestLinePaint.setPathEffect(effect);
        mTestLinePaint.setStrokeWidth(1);
        mTestLinePaint.setColor(Color.parseColor("#654321"));
    }

    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        mTextPaint.setTextSize(80);
        mTextPaint.clearShadowLayer();
//        mTextPaint.setStrokeCap(Paint.Cap.ROUND);
        mTextPaint.setShader(null);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setStrokeWidth(4);
        //Typeface font = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD);
        //mTextPaint.setTypeface(font);

        // 计算Baseline绘制的起点X轴坐标 ，计算方式：画布宽度的一半 - 文字宽度的一半
        int baseX = (int) (canvas.getWidth() / 2 - mTextPaint.measureText(TEXT) / 2);

        // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
        int baseY = (int) ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2));

        // 居中画一个文字
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        canvas.drawText(TEXT2, baseX, baseY, mTextPaint);
        mCanvasDrawHelper.drawLineY(canvas, baseY);

        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(TEXT2, baseX, baseY, mTextPaint);

        mTextPaint.setShadowLayer(1, 5, 5, Color.parseColor("#FFFF00"));
        canvas.drawText(TEXT2, baseX, baseY, mTextPaint);
        mTextPaint.clearShadowLayer();

        mLinearGradient = new LinearGradient(0, baseY, 0, baseY + ((mTextPaint.descent() + mTextPaint.ascent()) / 2),
                Color.parseColor("#00FF00"), Color.parseColor("#00FFFF"), Shader.TileMode.CLAMP);

        mTextPaint.setShader(mLinearGradient);
        mTextPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(TEXT2, baseX, baseY, mTextPaint);
        mTextPaint.setShader(null);

        // 横向长度测试 OK
        float w = mTextPaint.measureText(TEXT2);
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        Rect rect = new Rect();
        rect.top = 400 + fontMetricsInt.top;
        rect.bottom = 400 + fontMetricsInt.bottom;
        rect.left = 400;
        rect.right = (int) (400 + w);
        mCanvasDrawHelper.drawRect(canvas, rect);

        canvas.drawText(TEXT2, 400, 400, mTextPaint);

        // 横向长度测试 OK
        //canvas.translate(-500, -500);
        float spacemult = 1f;
        float width = mTextPaint.measureText(TEXT3, 0, 1);
        StaticLayout layout = new StaticLayout(TEXT3, mTextPaint, (int) width, Layout.Alignment.ALIGN_CENTER, spacemult, 0, false);
        rect.top = 500;
        rect.bottom = layout.getHeight() + 500;//(fontMetricsInt.descent - fontMetricsInt.ascent + (int) (fontMetricsInt.leading * 0.8f)) * TEXT3.length() + 500;
        rect.left = 500;
        rect.right = 500 + layout.getWidth();

        //canvas.drawRect(rect, mTestRectPaint);
        mCanvasDrawHelper.drawRect(canvas, rect);

        mCanvasDrawHelper.drawLineX(canvas, 500);
        mCanvasDrawHelper.drawLineY(canvas, 500);

        canvas.save();
        canvas.translate(500, 500);
        layout.draw(canvas);
        canvas.restore();

        canvas.drawText(fontMetricsInt.toString(), 0, 1000, mTextPaint);
        //Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        //canvas.drawLine(0, fontMetricsInt.top + baseY, 1920, fontMetricsInt.top + baseY, mTestLinePaint);
        //canvas.drawLine(0, fontMetricsInt.ascent + baseY, 1920, fontMetricsInt.ascent + baseY, mTestLinePaint);
        //canvas.drawLine(0, fontMetricsInt.descent + baseY, 1920, fontMetricsInt.descent + baseY, mTestLinePaint);
        //canvas.drawLine(0, fontMetricsInt.bottom + baseY, 1920, fontMetricsInt.bottom + baseY, mTestLinePaint);
        //canvas.drawLine(0, fontMetricsInt.leading + baseY, 1920, fontMetricsInt.leading + baseY, mTestLinePaint);

        //// 居中画一个文字
        //canvas.drawText(TEXT2, baseX, baseY + 100, mTextPaint);
        //mTextPaint.setStyle(Paint.Style.FILL);
        //// 居中画一个文字
        //canvas.drawText(TEXT2, baseX, baseY + 200, mTextPaint);
        //mTextPaint.setStrokeWidth(0);
        //// 居中画一个文字
        //canvas.drawText(TEXT2, baseX, baseY + 300, mTextPaint);
        //mTextPaint.setStrokeWidth(-4);
        //// 居中画一个文字
        //canvas.drawText(TEXT2, baseX, baseY + 400, mTextPaint);

        drawChangeSizeText(canvas);

        canvas.restore();
    }

    private void draw() {
        //drawShaderAndShadowLayer();
        //drawStroke();
    }

    private void drawChangeSizeText(Canvas canvas) {
        AbsTextSizeStrategy sizeStrategy = new RandomTextSizeStrategy(new int[]{80, 120}, TEXT4.length());
        int wordSpace = 0;
        int baseX = 300;
        int baseY = 300;
        for (int i = 0; i < TEXT4.length(); i++) {
            mCanvasDrawHelper.drawLineX(canvas, baseX);
            String subStr = TEXT4.charAt(i) + "";
            mTextPaint.setTextSize(sizeStrategy.getTextSize(i));
            int subStrW = (int) mTextPaint.measureText(subStr);
            canvas.drawText(subStr, baseX, baseY, mTextPaint);
            baseX = baseX + subStrW + wordSpace;
        }

        mTextPaint.setTextSize(80);
    }

    public static abstract class AbsTextSizeStrategy {
        protected int[] sizes;
        protected int len;

        public AbsTextSizeStrategy(int[] sizes, int len) {
            this.sizes = sizes;
            this.len = len;
        }

        abstract public int getTextSize(int index);
    }

    public static class OneTwoTextSizeStrategy extends AbsTextSizeStrategy {

        public OneTwoTextSizeStrategy(int[] sizes, int len) {
            super(sizes, len);
        }

        @Override
        public int getTextSize(int index) {
            return sizes[index % 2];
        }
    }

    public static class RandomTextSizeStrategy extends AbsTextSizeStrategy {

        private Random mRandom;

        public RandomTextSizeStrategy(int[] sizes, int len) {
            super(sizes, len);
            mRandom = new Random();
        }

        @Override
        public int getTextSize(int index) {
            int sizeIndex = mRandom.nextInt(sizes.length);
            return sizes[sizeIndex];
        }
    }
}
