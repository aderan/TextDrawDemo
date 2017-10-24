package com.aderan.textdrawdemo.story;

import android.view.View;

/**
 * @author fenglibin
 * @data 23/10/2017.
 */

public class ViewHost {

    View mView;

    public ViewHost(View view) {
        mView = view;
    }

    public int getWidth() {
        return mView.getWidth();
    }

    public int getHeight() {
        return mView.getHeight();
    }

    public void refreshDraw() {
        mView.postInvalidate();
    }
}
