package com.example.administrator.newlooklook.api;

import com.example.administrator.newlooklook.bean.NewsDetailBean;
import com.example.administrator.newlooklook.bean.NewsList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/5/24.
 */

public interface WangyiApi {
    @GET("http://c.m.163.com/nc/article/headline/T1348647909107/{id}-20.html")
    Observable<NewsList> getNews(@Path("id") int id );

    @GET("http://c.m.163.com/nc/article/{docId}//full.html")
    Observable<NewsDetailBean> getNewsDetail(@Path("docId") String docId);
}
