package com.aderan.textdrawdemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.NinePatchDrawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fenglibin on 01/04/2017.
 */

public class TextDrawView0 extends View {
    private static final String TEXT_STR = "故事集描述结构设置，不管不顾哈哈哈\n主动分行";
    private TextPaint mTextPaint;

    public TextDrawView0(Context context) {
        super(context);

        init();
    }

    public TextDrawView0(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public TextDrawView0(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(80);
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
        canvas.translate(200, 200);
        Path path = new Path();
        path.addCircle(200, 200, 200, Path.Direction.CCW);
        //canvas.clipRect(new RectF(0, 0, 400, 400));
        canvas.clipPath(path);

        mTextPaint.setColor(Color.parseColor("#3300FFFF"));
        mTextPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new RectF(0, 0, 400, 400), mTextPaint);

        mTextPaint.setColor(Color.BLACK);

        StaticLayout staticlayout = new StaticLayout(TEXT_STR, mTextPaint, 400, Layout.Alignment.ALIGN_CENTER, 1.8f, 0, false);
        staticlayout.draw(canvas);
        canvas.restore();
    }
}
