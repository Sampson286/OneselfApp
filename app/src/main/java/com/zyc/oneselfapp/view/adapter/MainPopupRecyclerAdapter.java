package com.zyc.oneselfapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyc.oneselfapp.R;
import com.zyc.oneselfapp.utilities.CommUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zyc on 2016/12/23.
 */

public class MainPopupRecyclerAdapter extends RecyclerView.Adapter<MainPopupRecyclerAdapter.MyViewHolder>{
    private List<String> list;

    public void setList(List<String> list) {
        this.list = list;
    }
    private OnItemClickListener onItemClickListener;
    private int selectIndex;

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<String> getList() {
        return list;
    }

    public MainPopupRecyclerAdapter(List<String> list) {
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=CommUtils.getViewById(R.layout.main_popupwindow_recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.main_popupwindow_recycler_item_textview.setText(list.get(position));
        if(selectIndex!=0&&position==selectIndex){
            holder.main_popupwindow_recycler_item_textview.setSelected(true);
        }
        if(onItemClickListener!=null){
            holder.main_popupwindow_recycler_item_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClickListener(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.main_popupwindow_recycler_item_textview)
        TextView main_popupwindow_recycler_item_textview;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
