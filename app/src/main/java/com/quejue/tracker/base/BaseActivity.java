package com.quejue.tracker.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.quejue.tracker.utils.ClickTracker;

/**
 * Created by chuan.shen on 2018/1/25.
 */
public class BaseActivity extends AppCompatActivity {
    private ClickTracker mTracker = new ClickTracker();

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mTracker.bind(getWindow().getDecorView().getRootView());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mTracker.bind(getWindow().getDecorView().getRootView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTracker.unbind();
    }
}
