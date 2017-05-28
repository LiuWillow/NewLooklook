package com.example.administrator.newlooklook.api;

import com.example.administrator.newlooklook.bean.Gank;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/5/19.
 */

public interface GankApi {
    @GET("api/data/福利/10/{page}")
    Observable<Gank> getGank(@Path("page") int page);
}
