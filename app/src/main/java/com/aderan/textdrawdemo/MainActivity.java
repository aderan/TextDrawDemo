package com.aderan.textdrawdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout mContainer;
    private ViewGroup mButtonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayUtil.init(this);

        mContainer = (FrameLayout) findViewById(R.id.container);
        mButtonLayout = (ViewGroup) findViewById(R.id.layout_buttons);

        for (int i = 0; i < mButtonLayout.getChildCount(); i++) {
            mButtonLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < mButtonLayout.getChildCount(); i++) {
            if (v == mButtonLayout.getChildAt(i)) {
                replaceWithIndex(i);
            }
        }
    }

    private void replaceWithIndex(int i) {
        View view = null;
        switch (i) {
            case 0:
                view = new TextDrawView0(this);
                break;
            case 1:
                view = new TextDrawView1(this);
                break;
            case 2:
                view = new TextDrawView2(this);
                break;
            case 3:
                view = new TextDrawView3(this);
                break;
            case 4:
                view = new TextDrawView4(this);
            default:
                break;
        }

        if (view != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            mContainer.removeAllViews();
            mContainer.addView(view, params);
        }
    }
}
