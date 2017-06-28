package com.zyc.oneselfapp.utilities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyc.oneselfapp.view.ApplicationData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zyc on 2016/12/16.
 */

public class CommUtils {
    public static String getPackageName(){
        return ApplicationData.globalContext.getPackageName();
    }
    public static int compareTime(String dateString){
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        int i = 0;
        try {
            i = date.compareTo(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * dp转为Px
     * @param dp
     * @return
     */
    public static int dip2Pixel(float dp){
        return (int)(dp*ApplicationData.globalContext.getResources().getDisplayMetrics().density+0.5);
    }

    /**
     * 通过ID得到View
     * @param id
     * @param root
     * @param attachToRoot
     * @return
     */
    public static View getViewById(int id, ViewGroup root,boolean attachToRoot){
        return LayoutInflater.from(ApplicationData.globalContext).inflate(id,root,attachToRoot);
    }

    /**
     * 通过ID来得到资源文件
     * @param id 需要的ID
     * @return 得到的资源
     */
    public  static String getResourceById(int id){
        return ApplicationData.globalContext.getResources().getString(id);
    }
}
