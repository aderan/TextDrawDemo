package com.aderan.textdrawdemo.story;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

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
    private final TextPaint mPaint;
    private final Paint mBorderPaint;
    private Layout.Alignment mAlignment;
    private TextBean mTextBean;

    private int mType = TYPE_TITLE;
    private int mLineMax;
    private float mSpaceMut = 1f;
    private float mSpaceAdd = 0;
    private float mMaxScale = 1f;
    private Typeface mTypeface;
    private Rect mViewRect = new Rect();
    private String mDrawText;

    private int offX;
    private int offY;
    private int textWidth;
    private int textHeight;

    public StoryText(View viewHost, TextBean bean) {
        mViewHost = new ViewHost(viewHost);
        mPaint = new TextPaint();

        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.GREEN);
        mBorderPaint.setStrokeWidth(2);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        updateTextBean(bean);
    }

    public void draw(Canvas canvas) {
        updateDrawView();

        float maxLineWidth = calcMaxLineWidth(mTextBean.hint, mPaint);
        TextPaint paintTmp = genTextPaint(maxLineWidth);
        StaticLayout layout = genStaticLayout(paintTmp);

        canvas.save();
        //Draw View Rect
        canvas.drawRect(mViewRect, mBorderPaint);
        //Draw Text Layout
        canvas.translate(offX, offY);
        layout.draw(canvas);
        canvas.restore();
    }

    private void updateDrawView() {
        offX = (int) (mTextBean.left * mViewHost.getWidth());
        offY = (int) (mTextBean.top * mViewHost.getHeight());
        textWidth = (int) (mTextBean.width * mViewHost.getWidth());
        //已宽度为基准的比例
        textHeight = (int) (mTextBean.height * mViewHost.getWidth());
        mViewRect.set(offX, offY, offX + textWidth, offY + textHeight);
    }

    public boolean isInText(int x, int y) {
        updateDrawView();
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

    public void updateTextBean(TextBean bean) {
        mTextBean = bean;
        if (mTextBean != null) {
            mSpaceAdd = mTextBean.spacing;

            if ("right".equals(mTextBean.gravity)) {
                mAlignment = Layout.Alignment.ALIGN_OPPOSITE;
            } else if ("center".equals(mTextBean.gravity))
                mAlignment = Layout.Alignment.ALIGN_CENTER;
            else
                mAlignment = Layout.Alignment.ALIGN_NORMAL;
            mLineMax = mTextBean.line;

            mType = getTypeByTextBean(mTextBean);

            mPaint.setTextSize(sp2px(mViewHost.mView.getContext(), mTextBean.size));
            mPaint.setColor(Color.parseColor(mTextBean.color));
            mMaxScale = mTextBean.size * 1f / mTextBean.minSize;
            mTypeface = TypefaceUtil.getTypeface(mTextBean.font);
            mPaint.setTypeface(mTypeface);
        }
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

    @NonNull
    private TextPaint genTextPaint(float maxLineWidth) {
        TextPaint paintTmp = mPaint;
        if (maxLineWidth > textWidth) {
            float scale = Math.min(maxLineWidth / textWidth, mMaxScale);
            paintTmp = new TextPaint(mPaint);
            paintTmp.setTypeface(mTypeface);
            paintTmp.setTextSize(mPaint.getTextSize() / scale);
        }
        return paintTmp;
    }

    @NonNull
    private StaticLayout genStaticLayout(TextPaint paintTmp) {
        StaticLayout layout = new StaticLayout(mTextBean.hint, paintTmp, textWidth, mAlignment, mSpaceMut, mSpaceAdd, false);
        if (mLineMax == 1 && layout.getLineCount() > 1) {
            final int lineEnd = layout.getLineEnd(0);
            for (int i = lineEnd; i > 0; i--) {
                //去除尾部/n
                if (mTextBean.hint.charAt(i - 1) == '\n')
                    continue;
                mDrawText = mTextBean.hint.substring(0, i) + "...";
                if (paintTmp.measureText(mDrawText) <= textWidth) {
                    break;
                }
            }
            layout = new StaticLayout(mDrawText, paintTmp, textWidth, mAlignment, mSpaceMut, mSpaceAdd, false);
        }
        return layout;
    }

    private static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getTypeByTextBean(TextBean bean) {
        int type;
        if ("title".equals(bean.type)) {
            type = TYPE_TITLE;
        } else if ("location".equals(bean.type)) {
            type = TYPE_LOCATION;
        } else if ("time".equals(bean.type)) {
            type = TYPE_TIME;
        } else {
            type = TYPE_DESC;
        }
        return type;
    }
}
