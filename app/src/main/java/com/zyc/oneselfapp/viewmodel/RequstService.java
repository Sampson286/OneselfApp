package com.zyc.oneselfapp.viewmodel;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by zyc on 2016/12/7.
 *
 * 临时创建一些用于请求的接口
 */

public interface RequstService {
    @GET
    Observable<CommPackageInfo> getCommPackageInfo(@Url String url);
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);

}
