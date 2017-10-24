package com.aderan.textdrawdemo.story;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import org.w3c.dom.Text;

/**
 * @author fenglibin
 * @data 23/10/2017.
 */

public class StoryText {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_LOCATION = 1;
    public static final int TYPE_TIME = 2;
    public static final int TYPE_DESC = 3;

    private final ViewHost mViewHost;
    private final TextBean mTextBean;
    private final TextPaint mPaint;
    private final Paint mBorderPaint;
    private Layout.Alignment mAlignment;

    private int mType = TYPE_TITLE;
    private float mSpaceMut = 1f;
    private float mSpaceAdd = 0;
    private float mMaxScale = 1f;
    private Typeface mTypeface;
    private Rect mViewRect = new Rect();

    public StoryText(View viewHost, TextBean bean) {
        mViewHost = new ViewHost(viewHost);
        mTextBean = bean;

        mPaint = new TextPaint();
        if (mTextBean != null) {
            mSpaceAdd = mTextBean.spacing;
            if ("right".equals(mTextBean.gravity)) {
                mAlignment = Layout.Alignment.ALIGN_OPPOSITE;
            } else if ("center".equals(mTextBean.gravity))
                mAlignment = Layout.Alignment.ALIGN_CENTER;
            else
                mAlignment = Layout.Alignment.ALIGN_NORMAL;

            mPaint.setTextSize(sp2px(mViewHost.mView.getContext(), mTextBean.size));
            mPaint.setColor(Color.parseColor(mTextBean.color));
            mMaxScale = mTextBean.size * 1f / mTextBean.minSize;
            mTypeface = TypefaceUtil.getTypeface(mTextBean.font);
            mPaint.setTypeface(mTypeface);
        }

        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.GREEN);
        mBorderPaint.setStrokeWidth(2);
        mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    public void draw(Canvas canvas) {
        int offX = (int) (mTextBean.left * mViewHost.getWidth());
        int offY = (int) (mTextBean.top * mViewHost.getHeight());
        int textWidth = (int) (mTextBean.width * mViewHost.getWidth());
        //已宽度为基准的比例
        int textHeight = (int) (mTextBean.height * mViewHost.getWidth());

        float maxLineWidth = calcMaxLineWidth(mTextBean.hint, mPaint);

        TextPaint paintTmp = mPaint;
        if (maxLineWidth > textWidth) {
            float scale = Math.min(maxLineWidth / textWidth, mMaxScale);
            paintTmp = new TextPaint(mPaint);
            paintTmp.setTextSize(mPaint.getTextSize() / scale);
        }

        //Default Layout Create;
        StaticLayout layout = new StaticLayout(mTextBean.hint, paintTmp, textWidth, mAlignment, mSpaceMut, mSpaceAdd, false);

        canvas.save();
        canvas.drawRect(new Rect(offX, offY, textWidth + offX, textHeight + offY), mBorderPaint);
        canvas.translate(offX, offY);
        layout.draw(canvas);
        canvas.restore();
    }

    public boolean isInText(int x, int y) {
        int offX = (int) (mTextBean.left * mViewHost.getWidth());
        int offY = (int) (mTextBean.top * mViewHost.getHeight());
        int textWidth = (int) (mTextBean.width * mViewHost.getWidth());
        //已宽度为基准的比例
        int textHeight = (int) (mTextBean.height * mViewHost.getWidth());
        mViewRect.set(offX, offY, offX + textWidth, offY + textHeight);
        return mViewRect.contains(x, y);
    }

    public int getType() {
        return mType;
    }

    public String getText() {
        return mTextBean.hint;
    }

    public void setText(String text) {
        mTextBean.hint = text;
        mViewHost.refreshDraw();
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private float calcMaxLineWidth(String text, TextPaint paint) {
        if (TextUtils.isEmpty(text)) {
            return 0;
        }
        String[] splits = text.split("\n");
        int maxCount = 0;
        int maxLine = 0;
        for (int i = 0; i < splits.length; i++) {
            if (splits[i].length() > maxCount) {
                maxLine = i;
                maxCount = splits[i].length();
            }
        }

        return paint.measureText(splits[maxLine]);
    }
}
