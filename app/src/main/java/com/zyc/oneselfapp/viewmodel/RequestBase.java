package com.zyc.oneselfapp.viewmodel;

import com.zyc.oneselfapp.R;
import com.zyc.oneselfapp.utilities.CommUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zyc on 2016/12/7.
 */

public class RequestBase {
    //创建一个单例对象
    private static RequestBase instance=null;
    //retrofit对象
    private Retrofit retrofit;
    //自定义一个OKHttpClient对象处理一些统一的请求
    private OkHttpClient okHttpClient;
    //添加一个请求拦截器做一下统一处理
    private HeardInterceptor heardInterceptor;
    //请求的接口类
    private RequstService requstService;
    String hot_url;
    public RequestBase() {
        instance=this;
        hot_url= CommUtils.getResourceById(R.string.lol_host);
        heardInterceptor=new HeardInterceptor();
        okHttpClient=new OkHttpClient().newBuilder()
                .addInterceptor(heardInterceptor)
                .build();
        retrofit=new Retrofit.Builder().baseUrl(hot_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        requstService=retrofit.create(RequstService.class);
    }

    public static RequestBase getInstance(){
        return (instance==null)?new RequestBase():instance;
    }
    public Observable<CommPackageInfo> getCommPackageInfo(String url){
        return requstService.getCommPackageInfo(url).compose(applyAsySchedulers());
    }
    public Observable<ResponseBody> downloadFile(String url){
       return requstService.downloadFile(url).compose(applyAsySchedulers());
    }

    private <T> Observable.Transformer<T,T> applyAsySchedulers(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new RequestFunc<T>());
            }
        };
    }
    //为请求添加请求头
    public class HeardInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            Request modifyRequest=request.newBuilder()
                    /*.header("sessionid","3c69bd8ec75a47ce9e5411c4b5986acb")
                    .header("X-Client","")*/
                    .build();
            return chain.proceed(modifyRequest);
        }
    }
    //进行数据的转化或组合
    public class RequestFunc<T> implements Func1<T,T> {
        @Override
        public T call(T t) {
            /*String codeValue=null;
            String msgValue=null;
            try {
                Field code=t.getClass().getDeclaredField("code");
                Field msg=t.getClass().getDeclaredField("msg");
                if(!code.isAccessible()){
                    code.setAccessible(true);
                }
                msg.setAccessible(true);
                codeValue=(String)code.get(t);
                msgValue=(String)msg.get(t);
                if(codeValue.equals("101")){
                    throw new ApiException(codeValue,msgValue);
                }
                Log.i("CodeValue",codeValue);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }*/
            return t;
        }
    }
}
