package com.example.administrator.newlooklook.api;

import com.example.administrator.newlooklook.bean.NewsList;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ApiManger {
    private static ApiManger apiManger;
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .build();
    public static ApiManger getInstance(){
        if(apiManger==null){
            synchronized (ApiManger.class){
                apiManger=new ApiManger();
            }
        }
        return apiManger;
    }

    private GankApi gankApi;
    public GankApi getGankService(){
        if(gankApi==null){
            gankApi=new Retrofit.Builder()
                    .baseUrl("http://gank.io/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(GankApi.class);
        }
        return gankApi;
    }

    //TODO 网易service
    private WangyiApi wangyiApi;
    public WangyiApi getWangyiService(){
        if(wangyiApi==null){
            wangyiApi=new Retrofit.Builder()
                    .baseUrl("http://c.m.163.com")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build().create(WangyiApi.class);
        }
        return wangyiApi;
    }
}
