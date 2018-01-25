package com.quejue.tracker.utils;


import com.quejue.tracker.R;
import com.quejue.tracker.annotations.DataId;

/**
 * Created by chuan.shen on 2018/1/23.
 */
public enum EventEnum {
    @DataId({R.id.login_wechat, R.id.login_qq})
    CLICK_LOGIN,
    @DataId({R.id.register})
    CLICK_REGISTER,
    @DataId({R.id.logout})
    CLICK_LOGOUT,
    @DataId({R.id.fragment_btn})
    CLICK_FRAGMENT_BUTTON
}
