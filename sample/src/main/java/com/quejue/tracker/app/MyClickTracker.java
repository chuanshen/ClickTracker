package com.quejue.tracker.app;

import android.view.View;
import android.widget.Toast;

import com.quejue.clicktracker.ClickTracker;

/**
 * Created by chuan.shen on 2018/1/25.
 */
public class MyClickTracker extends ClickTracker {
    @Override
    public void clickTracker(View view, String EventId) {
        Toast.makeText(view.getContext(), "EventId = " + EventId, Toast.LENGTH_SHORT).show();
    }
}
