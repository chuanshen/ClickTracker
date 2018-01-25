package com.quejue.clicktracker.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.quejue.clicktracker.ClickTracker;

/**
 * Created by chuan.shen on 2018/1/25.
 */
public abstract class TrackerActivity extends Activity {
    private ClickTracker mTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTracker = getClickTracker();
    }

    protected abstract ClickTracker getClickTracker();

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        if (mTracker != null) {
            mTracker.bind(getWindow().getDecorView().getRootView());
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (mTracker != null) {
            mTracker.bind(getWindow().getDecorView().getRootView());
        }
    }

    @Override
    protected void onDestroy() {
        if (mTracker != null) {
            mTracker.unbind();
        }
        super.onDestroy();
    }
}
