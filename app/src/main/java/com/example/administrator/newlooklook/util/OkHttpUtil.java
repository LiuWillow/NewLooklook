package com.example.administrator.newlooklook.util;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/26.
 */

public class OkHttpUtil {
    private static OkHttpUtil instance;
    private OkHttpClient client;
    private Handler mDelivery;
    private OkHttpUtil(){
        client=new OkHttpClient();
        client.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        mDelivery=new Handler(Looper.getMainLooper());
    }
    public static OkHttpUtil getInstance(){
        if(instance==null){
            instance=new OkHttpUtil();
        }
        return instance;
    }
    public static void get(String url, ResultCallback callback) {
        getInstance().getRequest(url, callback);
    }

    private void getRequest(String url,final ResultCallback callback){
        final Request request=new Request.Builder().url(url).build();
        deliveryResult(callback,request);
    }

    private void deliveryResult(final ResultCallback callback,Request request){
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(callback,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    if (callback.mType == String.class) {
                        sendSuccessCallBack(callback, str);
                    } else {
                        Object object = JsonUtils.deserialize(str, callback.mType);
                        sendSuccessCallBack(callback, object);
                    }
                } catch (final Exception e) {
                    sendFailCallback(callback, e);
                }
            }
        });
    }

    private void sendFailCallback(final ResultCallback callback, final Exception e){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        });
    }
    private void sendSuccessCallBack(final ResultCallback callback, final Object obj) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(obj);
                }
            }
        });
    }

    public static abstract class ResultCallback<T>{
        Type mType;
        public ResultCallback(){
            mType=getSuperclassTypeParameter(getClass());
        }
        static Type getSuperclassTypeParameter(Class<?> subclass){
            Type superclass=subclass.getGenericSuperclass();
            if(superclass instanceof Class){
                throw new RuntimeException("miss type parameter");
            }
            ParameterizedType parameterized=(ParameterizedType)superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onSuccess(T response);
        public abstract void onFailure(Exception e);
    }
}
