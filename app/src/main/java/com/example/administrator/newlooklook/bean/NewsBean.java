package com.example.administrator.newlooklook.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/24.
 */

public class NewsBean implements Serializable {
    @SerializedName("docid")
    private String docid;
    @SerializedName("title")
    private String title;
    @SerializedName("imgsrc")
    private String imgsrc;
    @SerializedName("source")
    private String source;

    public String getDocid(){
        return docid;
    }
    public String getTitle(){
        return title;
    }
    public String getImgsrc(){
        return imgsrc;
    }
    public String getSource(){
        return source;
    }
}
