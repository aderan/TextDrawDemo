package com.aderan.textdrawdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.aderan.textdrawdemo.story.StoryText;
import com.aderan.textdrawdemo.story.StoryViewLayer;
import com.aderan.textdrawdemo.story.TextBean;

public class Main2Activity extends AppCompatActivity {
    StoryViewLayer mStoryViewLayer;
    EditText mEditText;
    private StoryText mStoryText;
    private int mStoryTextType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mEditText = (EditText) findViewById(R.id.EditText);
        mStoryViewLayer = (StoryViewLayer) findViewById(R.id.StoryViewLayer);
        mStoryViewLayer.setOnTextClickListener(new StoryViewLayer.OnTextClickListener() {

            @Override
            public void onTextClick(StoryText text) {
                mStoryText = text;
                mStoryTextType = text.getType();

                mEditText.setText(mStoryText.getText());
            }
        });

        mockData();

        findViewById(R.id.Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    mStoryViewLayer.updateText(mStoryText, text);
                }
            }
        });
    }

    private void mockData() {
        TextBean textBean;

        textBean = new TextBean();
        textBean.left = 0.4f;
        textBean.top = 0.3f;
        textBean.hint = "春眠不觉晓，处处闻啼鸟。";
        textBean.size = 20;
        textBean.minSize = 8;
        textBean.color = "#FF0000";
        textBean.style = "";
        textBean.spacing = 2;
        textBean.gravity = "center";
        textBean.type = "desc";
        textBean.width = 0.2f;
        textBean.height = 0.2f;
        textBean.font = "/sdcard/2.ttf";
        textBean.line = 2;
        mStoryViewLayer.addData(textBean);

        textBean = new TextBean();
        textBean.left = 0.3f;
        textBean.top = 0.1f;
        textBean.hint = "春晓*孟浩然";
        textBean.size = 30;
        textBean.minSize = 16;
        textBean.color = "#00FF00";
        textBean.style = "";
        textBean.spacing = 2;
        textBean.gravity = "left";
        textBean.type = "title";
        textBean.width = 0.4f;
        textBean.height = 0.2f;
        textBean.font = "/sdcard/3.ttf";
        textBean.line = 1;
        mStoryViewLayer.addData(textBean);

        textBean = new TextBean();
        textBean.left = 0.2f;
        textBean.top = 0.7f;
        textBean.hint = "2018.07.19";
        textBean.size = 12;
        textBean.minSize = 12;
        textBean.color = "#00FF00";
        textBean.style = "";
        textBean.spacing = 2;
        textBean.gravity = "left";
        textBean.type = "time";
        textBean.width = 0.2f;
        textBean.height = 0.2f;
        //textBean.font = "/sdcard/3.ttf";
        textBean.line = 1;
        mStoryViewLayer.addData(textBean);

        textBean = new TextBean();
        textBean.left = 0.6f;
        textBean.top = 0.6f;
        textBean.hint = "杭州.黄龙时代广场.九言科技";
        textBean.size = 12;
        textBean.minSize = 8;
        textBean.color = "#00FF00";
        textBean.style = "";
        textBean.spacing = 2;
        textBean.gravity = "left";
        textBean.type = "location";
        textBean.width = 0.3f;
        textBean.height = 0.1f;
        //textBean.font = "/sdcard/3.ttf";
        textBean.line = 1;
        mStoryViewLayer.addData(textBean);

        textBean = new TextBean();
        textBean.left = 0.6f;
        textBean.top = 0.8f;
        textBean.hint = "杭州.黄龙时代广场";
        textBean.size = 12;
        textBean.minSize = 12;
        textBean.color = "#00FF00";
        textBean.style = "";
        textBean.spacing = 2;
        textBean.gravity = "right";
        textBean.type = "location";
        textBean.width = 0.3f;
        textBean.height = 0.1f;
        //textBean.font = "/sdcard/3.ttf";
        textBean.line = 1;
        mStoryViewLayer.addData(textBean);
    }

}
