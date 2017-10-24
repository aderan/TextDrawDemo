package com.aderan.textdrawdemo.story;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fenglibin
 * @data 23/10/2017.
 */

public class StoryViewLayer extends View {
    List<StoryText> storyTexts = new ArrayList<>();
    private OnTextClickListener mOnTextClickListener;

    public StoryViewLayer(Context context) {
        super(context);
    }

    public StoryViewLayer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StoryViewLayer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addData(TextBean bean) {
        storyTexts.add(new StoryText(this, bean));
    }

    public void updateText(StoryText storyText, String text) {
        storyText.setText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (StoryText storyText : storyTexts) {
            storyText.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < storyTexts.size(); i++) {
                    if (storyTexts.get(i).isInText(eventX, eventY)) {
                        if (mOnTextClickListener != null)
                            mOnTextClickListener.onTextClick(storyTexts.get(i), storyTexts.get(i).getType());
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setOnTextClickListener(OnTextClickListener listener) {
        mOnTextClickListener = listener;
    }

    public interface OnTextClickListener {
        void onTextClick(StoryText text, int type);
    }
}
