package com.aderan.textdrawdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fenglibin on 01/04/2017.
 */

public class TextDrawView4 extends View {

    public static String[] FONTS = {
            "fangzhengjinlei.ttf",
            "fangzhengpangwa.ttf",
            "huakangshaonvti.ttf",
            "heiti.ttf",
            "MFLiHei-Regular.ttf",
            "simfang.ttf"};

    private static int fonts_index = 0;

    private static int LIMIT_W = 512;
    private static int LIMIT_H = 1024;
    private static int SPACE_VERTICAL_LINE = 15;
    private static int SPACE_VERTICAL_WORD = 5;
    private static float SPACE_MUT = 1.2f;

    public static final String TEXT4 = "A吃一把泥土吃一\n把泥土吃一把泥土";
    public static final String TEXT3 = "春晓·孟浩然\n春眠不觉晓\n处处闻啼鸟\n夜来风雨声\n花落知多少\n";

    private TextPaint mTextPaint;
    private Paint mTestRectPaint;
    private Paint mTestLinePaint;
    private Typeface mTypeface;
    private CanvasDrawHelper mCanvasDrawHelper;
    private Rect rect = new Rect();

    public TextDrawView4(Context context) {
        super(context);

        init();
    }

    public TextDrawView4(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public TextDrawView4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mCanvasDrawHelper = new CanvasDrawHelper();

        Typeface mTypeface = Typeface.createFromAsset(getResources().getAssets(), FONTS[(fonts_index++) % FONTS.length]);
        mTextPaint = new TextPaint();
        mTextPaint.setTypeface(mTypeface);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        mTextPaint.setTextSize(160);
        mTextPaint.clearShadowLayer();
        mTextPaint.setShader(null);
        mTextPaint.setStyle(Paint.Style.FILL);

        // 横向长度测试 OK
        String[] strArray = TEXT3.split("\n");
        int len = 0;
        int index = 0;
        for (int i = 0; i < strArray.length; i++) {
            if (strArray[i].length() > len) {
                index = i;
                len = strArray[i].length();
            }
        }

        //TODO：中英文处理
        int wOne = (int) mTextPaint.measureText(strArray[index], 0, 1);
        int w = wOne * strArray.length + SPACE_VERTICAL_LINE * (strArray.length - 1);

        StaticLayout layout = new StaticLayout(strArray[index], mTextPaint, wOne, Layout.Alignment.ALIGN_NORMAL, SPACE_MUT, SPACE_VERTICAL_WORD, false);
        int h = layout.getHeight();

        if (w > LIMIT_W || h > LIMIT_H) {
            float scale = Math.min(Math.min(LIMIT_W * 1f / w, LIMIT_H * 1f / h), 1f);
            mTextPaint.setTextSize(mTextPaint.getTextSize() * scale);
            wOne = (int) mTextPaint.measureText(strArray[index], 0, 1);
            w = wOne * strArray.length + SPACE_VERTICAL_LINE * (strArray.length - 1);

            layout = new StaticLayout(strArray[index], mTextPaint, wOne, Layout.Alignment.ALIGN_NORMAL, SPACE_MUT, SPACE_VERTICAL_WORD, false);
            h = layout.getHeight();
        }

        int offX = 100;
        int offY = 200;

        rect.left = offX;
        rect.right = offX + w;
        rect.top = offY;
        rect.bottom = offY + h;

        for (int i = 0; i < strArray.length; i++) {
            int pos = fonts_index % 2 == 1 ? strArray.length - i - 1 : i;
            canvas.save();
            canvas.translate(offX + pos * (wOne + SPACE_VERTICAL_LINE), offY);
            layout = new StaticLayout(strArray[i], mTextPaint, wOne, Layout.Alignment.ALIGN_NORMAL, SPACE_MUT, SPACE_VERTICAL_WORD, false);
            layout.draw(canvas);
            canvas.restore();
        }

        mCanvasDrawHelper.drawRect(canvas, rect);
        mCanvasDrawHelper.drawLineX(canvas, offX);
        mCanvasDrawHelper.drawLineY(canvas, offY);

        canvas.restore();
    }

    private int measureWidth(String text, TextPaint paint) {
        String[] strArray = text.split("\n");
        int len = 0;
        int index = 0;
        for (int i = 0; i < strArray.length; i++) {
            if (strArray[i].length() > len) {
                index = i;
                len = strArray[i].length();
            }
        }
        return (int) paint.measureText(strArray[index]);
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
