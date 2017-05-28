package com.example.administrator.newlooklook.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/19.
 */

public class Gank {
    private boolean error;
    private ArrayList<Meizi> results;
    public boolean isError(){
        return error;
    }
    public ArrayList<Meizi> getResult(){
        return results;
    }
}
