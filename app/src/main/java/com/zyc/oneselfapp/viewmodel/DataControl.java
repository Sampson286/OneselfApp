package com.zyc.oneselfapp.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.util.Log;

import com.zyc.oneselfapp.utilities.AppConstant;
import com.zyc.oneselfapp.utilities.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by zyc on 2016/12/19.
 * 数据请求类
 */

public class DataControl {
    private static DataControl instance=null;
    private static RequestBase requsstBase=RequestBase.getInstance();
    public DataControl(){
        instance=this;
    }
    public static DataControl getInstance(){
        return instance==null?new DataControl():instance;
    }
    public Observable<CommPackageInfo> getCommPackageInfo( String url){
        return requsstBase.getCommPackageInfo(url);
    }
    public Observable<ResponseBody> downloadFile(String url){
       return requsstBase.downloadFile(url);
    }
    public GoodsList getGoodList(){
        Log.i("getGoodList", "startTime:"+ SystemClock.uptimeMillis());
        GoodsList goodsList=null;
        File goodsFile=new File(AppConstant.goodsListPath);
        if(goodsFile.exists()){
            goodsList= FileUtils.getBeanByFile(goodsFile,GoodsList.class);
        }
        Log.i("getGoodList", "endTime:"+ SystemClock.uptimeMillis());
        return  goodsList;
    }

    public List<Bitmap> getGoodsImge(){
        List<Bitmap> goodsImg=new ArrayList<Bitmap>();
        File goodsImgFile=new File(AppConstant.goodImgPath);
        if(goodsImgFile.exists()){
            for (File imgFile:goodsImgFile.listFiles()){
                Bitmap bitmap= BitmapFactory.decodeFile(imgFile.getPath());
                goodsImg.add(bitmap);
            }
        }
        return  goodsImg;
    }
    public Good getGoodById(String id){
        Good good=null;
        File goodFile=new File(AppConstant.goodDetailePath+"good_"+id+".js");
        if(goodFile.exists()){
            good=FileUtils.getBeanByFile(goodFile,Good.class);
        }
        return good;
    }

    /**
     * 根据所属类型进行筛选
     * @param goodsList 所有数据
     * @param prop 筛选类型
     * @return
     */
    public GoodsList getGoodsListByProp(GoodsList goodsList,String prop){
        Iterator<GoodsList.ItemsEntity> iterator=goodsList.getItems().iterator();
        while (iterator.hasNext()){
            GoodsList.ItemsEntity item=iterator.next();
            if(!item.getProplist().contains(prop)){
                iterator.remove();
            }
        }
        return  goodsList;
    }

    /**
     * 通过地图分类信息
     * @param goodsList
     * @param map
     * @return
     */
    public GoodsList getGoodsListByMap(GoodsList goodsList,String map){
        Iterator<GoodsList.ItemsEntity> iterator=goodsList.getItems().iterator();
        while (iterator.hasNext()){
            GoodsList.ItemsEntity item=iterator.next();
            if(!item.getMaplist().contains(map)){
                iterator.remove();
            }
        }
        return  goodsList;
    }
}
