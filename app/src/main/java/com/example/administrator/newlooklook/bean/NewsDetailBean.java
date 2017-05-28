package com.example.administrator.newlooklook.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class NewsDetailBean implements Serializable{
    /**
     * docid
     */
    @SerializedName("docid")
    private String docid;
    /**
     * title
     */
    @SerializedName("title")
    private String title;
    /**
     * source
     */
    @SerializedName("source")
    private String source;
    /**
     * body
     */
    @SerializedName("body")
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBody() {
        return body;
    }

}
