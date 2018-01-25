package com.quejue.tracker.app;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.quejue.tracker.R;
import com.quejue.tracker.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment();
        findViewById(R.id.login_wechat).setOnClickListener(this);
        findViewById(R.id.login_qq).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
    }

    private void showFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment fragment = new HomeFragment();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
    }
}
