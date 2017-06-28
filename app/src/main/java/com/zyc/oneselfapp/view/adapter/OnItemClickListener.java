package com.zyc.oneselfapp.view.adapter;

import android.view.View;

/**
 * Created by zyc on 2016/12/20.
 * RecyclerView的item的点击事件
 */

public interface OnItemClickListener {
    /**
     * Item的点击事件
     * @param view 当前的视图
     * @param position 当前的索引
     */
    void OnItemClickListener(View view,int position);
}
