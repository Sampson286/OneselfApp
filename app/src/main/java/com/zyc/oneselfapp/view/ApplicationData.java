package com.zyc.oneselfapp.view;

import android.app.Activity;
import android.app.Application;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zyc on 2016/12/7.
 */

public class ApplicationData extends Application {
    //创建一个根路径
    //public static String softwareCatalog;
    //创建一个全局的环境变量
    public static ApplicationData globalContext=null;
    // 用于保存当前打开的activity,为了不影响系统回收activity
    private final HashMap<String, SoftReference<Activity>> activityMaps = new HashMap<String, SoftReference<Activity>>();

    @Override
    public void onCreate() {
        super.onCreate();
        globalContext=this;
    }
    /**
     * 将打开的activity添加到map集合
     *
     * @param activity
     *            打开的activity
     */
    public void addActivityToMap(Activity activity) {
        activityMaps.put(activity.getClass().getName(), new SoftReference<Activity>(
                activity));
    }
    /**
     * activity关闭时 将其从列表中移除 便于系统及时回收
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activityMaps.remove(activity.toString());
    }
    /**
     * 循环遍历并退出列表中打开的activity
     */
    public void exit() {
        for (Iterator<Map.Entry<String, SoftReference<Activity>>> iterator = activityMaps
                .entrySet().iterator(); iterator.hasNext();) {
            SoftReference<Activity> activityReference = iterator.next()
                    .getValue();
            Activity activity = activityReference.get();
            if (activity != null) {
                activity.finish();
            }
        }
        activityMaps.clear();
        System.exit(0);
    }
}
