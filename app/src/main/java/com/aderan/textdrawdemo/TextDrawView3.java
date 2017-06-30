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

public class TextDrawView3 extends View {

    public static String[] FONTS = {
            "fangzhengjinlei.ttf",
            "fangzhengpangwa.ttf",
            "huakangshaonvti.ttf",
            "heiti.ttf",
            "MFLiHei-Regular.ttf",
            "simfang.ttf"};

    private static int index = 0;

    public static final String TEXT3 =
            "Sonnet 106\n\n" +
                    "When in the chronicle of wasted time\n" +
                    "I see descriptions of the fairest wights,\n" +
                    "And beauty making beautiful old rhyme\n" +
                    "In praise of ladies dead, and lovely knights,\n" +
                    "Then, in the blazon of sweet beauty’s best,\n" +
                    "Of hand, of foot, of lip, of eye, of brow,\n" +
                    "I see their antique pen would have express’d\n" +
                    "Even such a beauty as you master now.\n" +
                    "So all their praises are but prophecies\n" +
                    "Of this our time, all you prefiguring;\n" +
                    "And, for they look’d but with divining eyes,\n" +
                    "They had not skill enough your worth to sing:\n" +
                    "For we, which now behold these present days,\n" +
                    "Had eyes to wonder, but lack tongues to praise.";

    private TextPaint mTextPaint;

    private Paint mTestRectPaint;
    private Paint mTestLinePaint;
    private Typeface mTypeface;
    private CanvasDrawHelper mCanvasDrawHelper;
    private Rect rect = new Rect();

    public TextDrawView3(Context context) {
        super(context);

        init();
    }

    public TextDrawView3(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public TextDrawView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mCanvasDrawHelper = new CanvasDrawHelper();

        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), FONTS[(index++) % FONTS.length]);
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
        mTextPaint.setTextSize(160);
        mTextPaint.clearShadowLayer();
        mTextPaint.setShader(null);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.RED);

        // 横向长度测试 OK
        int limit_w = 1024;
        int limit_h = 1024;
        //int w = measureWidth(TEXT3, mTextPaint);
        //if (w > limit_w) {
        //    mTextPaint.setTextSize(mTextPaint.getTextSize() * limit_w * 1f/ w);
        //    w = measureWidth(TEXT3, mTextPaint);
        //}
//
        //float spacemult = 1f;
        //StaticLayout layout = new StaticLayout(TEXT3, mTextPaint, w, Layout.Alignment.ALIGN_NORMAL, spacemult, 0, false);
        //int h = layout.getHeight();
        //if (h > limit_h) {
        //    mTextPaint.setTextSize(mTextPaint.getTextSize() * limit_h * 1f/ h);
        //    w = measureWidth(TEXT3, mTextPaint);
        //    layout = new StaticLayout(TEXT3, mTextPaint, w, Layout.Alignment.ALIGN_NORMAL, spacemult, 0, false);
        //}
//
        //rect.top = 100;
        //rect.bottom = layout.getHeight() + 100;//(fontMetricsInt.descent - fontMetricsInt.ascent + (int) (fontMetricsInt.leading * 0.8f)) * TEXT3.length() + 500;
        //rect.left = 100;
        //rect.right = 100 + layout.getWidth();
        ////canvas.drawRect(rect, mTestRectPaint);
        //mCanvasDrawHelper.drawRect(canvas, rect);
        //mCanvasDrawHelper.drawLineX(canvas, 100);
        //mCanvasDrawHelper.drawLineY(canvas, 100);
//
        //canvas.save();
        //canvas.translate(100, 100);
        //layout.draw(canvas);
        //canvas.restore();

        //Process Type 2
        mTextPaint.setTextSize(80);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.RED);


        String[] lines = TEXT3.split("\n");
        int len = 0;
        int index = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() > len) {
                index = i;
                len = lines[i].length();
            }
        }

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        int w = (int) mTextPaint.measureText(lines[index]);
        int h = (int) (fontMetrics.bottom - fontMetrics.top) * lines.length;

        if (w > limit_w || h > limit_h) {
            float scale = Math.min(Math.min(limit_w * 1f / w, limit_h * 1f / h), 1f);
            mTextPaint.setTextSize((float) Math.floor(mTextPaint.getTextSize() * scale));
            fontMetrics = mTextPaint.getFontMetrics();

            w = (int) mTextPaint.measureText(lines[index]);
            h = (int) (fontMetrics.bottom - fontMetrics.top) * lines.length;
        }

        rect.top = 100;
        rect.bottom = h + 100;
        rect.left = 100;
        rect.right = 100 + w;

        mCanvasDrawHelper.drawRect(canvas, rect);
        mCanvasDrawHelper.drawLineX(canvas, 100);
        mCanvasDrawHelper.drawLineY(canvas, 100);
        mCanvasDrawHelper.drawLineX(canvas, 100 + w);

        canvas.save();
        fontMetrics = mTextPaint.getFontMetrics();
        canvas.translate(100, 100);
        for (int i = 0; i < lines.length; i++) {
            float baseY = -fontMetrics.top + i * (fontMetrics.bottom - fontMetrics.top);
            canvas.drawText(lines[i], 0, baseY, mTextPaint);
        }
        canvas.restore();

        canvas.restore();
    }

    class LayoutCaculator {
        private int limit_w = 512;
        private int limit_h = 100;

        private String text;
        private TextPaint paint;

        public LayoutCaculator(String text, TextPaint paint) {
            this.text = text;
            this.paint = paint;
        }
    }
}
