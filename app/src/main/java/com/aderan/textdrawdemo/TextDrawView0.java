package com.aderan.textdrawdemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
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
    private NinePatchDrawable mNinePatchDrawable;

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
        //mDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
        //        new int[]{0xFFFF0000, 0xFF00FF00,
        //                0xFF0000FF});

        Bitmap bitmap = getImageFromAssetsFile("process_bg.png");
        //mNinePatchDrawable = NinePatchBitmapFactory.createNinePathWithCapInsets(getResources(), bitmap, 9, 9, 52, 42, null);

        byte[] ninePatchChunk = bitmap.getNinePatchChunk();
        //if (NinePatch.isNinePatchChunk(ninePatchChunk)) {
        //    Toast.makeText(getContext(), "是NinePatchChunk", Toast.LENGTH_SHORT).show();
        //}
        mNinePatchDrawable = new NinePatchDrawable(getResources(), bitmap, bitmap.getNinePatchChunk(), new Rect(), null);
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

        mNinePatchDrawable.setBounds(new Rect(0, 0, 100, 100));
        mNinePatchDrawable.draw(canvas);

        mNinePatchDrawable.setBounds(new Rect(0, 100, 200, 300));
        mNinePatchDrawable.draw(canvas);

        mNinePatchDrawable.setBounds(new Rect(0, 300, 400, 700));
        mNinePatchDrawable.draw(canvas);
    }
}
