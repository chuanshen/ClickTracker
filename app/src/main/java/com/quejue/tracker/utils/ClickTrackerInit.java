package com.quejue.tracker.utils;

import com.quejue.tracker.internal.IBindEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chuan.shen on 2018/1/24.
 */
public class ClickTrackerInit {
   private static final String CLASS_NAME = "com.quejue.clicktracker.ClickTrackerEvents";
   public static Map<Integer, String> getEventMap() {
      Map<Integer, String> routeMap = new HashMap<>();
      try {
         Class clazz = Class.forName(CLASS_NAME);
         IBindEvent interceptor = (IBindEvent) clazz.newInstance();
         interceptor.bindEvent(routeMap);
      } catch (Exception e) {
         e.printStackTrace();
      }

      return routeMap;
   }
}
