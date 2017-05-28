package com.example.administrator.newlooklook.util;

import com.example.administrator.newlooklook.bean.NewsDetailBean;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by Administrator on 2017/5/26.
 */

public class NewsJsonUtils {

    public static NewsDetailBean readJsonNewsDetailBeans(String res, String docId) {
        NewsDetailBean newsDetailBean = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(docId);
            if(jsonElement == null) {
                return null;
            }
            newsDetailBean = JsonUtils.deserialize(jsonElement.getAsJsonObject(), NewsDetailBean.class);
        } catch (Exception e) {
        }
        return newsDetailBean;
    }
}
