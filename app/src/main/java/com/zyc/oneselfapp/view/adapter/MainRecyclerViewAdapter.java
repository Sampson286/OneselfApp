package com.zyc.oneselfapp.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyc.oneselfapp.R;
import com.zyc.oneselfapp.utilities.AppConstant;
import com.zyc.oneselfapp.utilities.CommUtils;
import com.zyc.oneselfapp.viewmodel.GoodsList;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zyc on 2016/12/20.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder>{
    private GoodsList goodsList;
    private OnItemClickListener onItemClickListener;

    public void setGoodsList(GoodsList goodsList) {
        this.goodsList = goodsList;
    }

    public MainRecyclerViewAdapter(GoodsList goodsList) {
        this.goodsList=goodsList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder=new MyViewHolder(CommUtils.getViewById(R.layout.main_recycler_item,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(goodsList.getItems().get(position).getName());
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(AppConstant.goodImgPath+ File.separator+goodsList.getItems().get(position).getGood_id()+".png"));
        if(onItemClickListener!=null){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClickListener(holder.imageView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return goodsList.getItems().size();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.main_recyclerview_item_img)
        ImageView imageView;
        @BindView(R.id.main_recyclerview_item_text)
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
