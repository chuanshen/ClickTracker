package com.quejue.clicktracker;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by chuan.shen on 2018/1/22.
 */
public abstract class ClickTracker extends View.AccessibilityDelegate {
    private WeakReference<View> mRootView;
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
    private Map<Integer, String> mEventMap;

    public ClickTracker() {
        mEventMap = ClickTrackerInit.getEventMap();
    }

    public void bind(View rootView) {
        if ((rootView != null) && (rootView.getViewTreeObserver() != null)) {
            mRootView = new WeakReference(rootView);
            mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    View root = (mRootView == null) ? null : mRootView.get();
                    if ((root != null) && (root.getViewTreeObserver() != null) && root.getViewTreeObserver().isAlive()) {
                        installAccessibilityDelegate(root);
                    }
                }
            };
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
        }
    }

    public void unbind() {
        View root = (mRootView == null) ? null : mRootView.get();
        if ((root != null) && (root.getViewTreeObserver() != null) && root.getViewTreeObserver().isAlive()) {
            uninstallAccessibilityDelegate(root);
        }

        mRootView.clear();
    }

    private void installAccessibilityDelegate(View view) {
        if (view != null) {
            view.setAccessibilityDelegate(ClickTracker.this);
            if (view instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) view;
                int count = parent.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = parent.getChildAt(i);
                    if (child.getVisibility() != View.GONE) {
                        installAccessibilityDelegate(child);
                    }
                }
            }
        }
    }

    private void uninstallAccessibilityDelegate(View view) {
        if (view != null) {
            view.setAccessibilityDelegate(null);
            if (view instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) view;
                int count = parent.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = parent.getChildAt(i);
                    if (child.getVisibility() != View.GONE) {
                        uninstallAccessibilityDelegate(null);
                    }
                }
            }
        }
    }

    @Override
    public void sendAccessibilityEvent(View view, int eventType) {
        super.sendAccessibilityEvent(view, eventType);
        if ((view != null) && (AccessibilityEvent.TYPE_VIEW_CLICKED == eventType)) {
            String eventId = mEventMap.get(view.getId());
            if (TextUtils.isEmpty(eventId)) {
                return;
            }
            clickTracker(view, eventId);
        }
    }

    public abstract void clickTracker(View view, String EventId);
}