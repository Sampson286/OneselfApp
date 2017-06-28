package com.zyc.oneselfapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zyc on 2016/12/7.
 * 创建一个Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    /** 布局转换器*/
    public LayoutInflater mInflater;
    /** 布局内容视图*/
    protected View mContentView;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //添加到集合用于退出时销毁
        ApplicationData.globalContext.addActivityToMap(this);
        mInflater = LayoutInflater.from(this);
        int layoutId = getLayoutId();
        if(layoutId != 0){
            setContentView(layoutId);
        } else if(null != mContentView){
            setContentView(mContentView);
        } else {
            throw new IllegalStateException("layoutId can not be 0 or contentView should be setted");
        }
        unbinder=ButterKnife.bind(this);
        initialized();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 布局文件ID
     * @return
     */
    protected abstract int getLayoutId();
    /**
     *
     */
    protected abstract void initialized();
}
