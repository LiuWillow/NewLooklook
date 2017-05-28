package com.example.administrator.newlooklook.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/24.
 */

public class NewsList {
    @SerializedName("T1348647909107")
    private ArrayList<NewsBean> newsList;
    public ArrayList<NewsBean> getNewsList(){
        return newsList;
    }
}
