package com.example.administrator.newlooklook.presenter;

import android.support.annotation.MainThread;

import com.example.administrator.newlooklook.api.ApiManger;
import com.example.administrator.newlooklook.bean.NewsDetailBean;
import com.example.administrator.newlooklook.presenter.implPresenter.IWangyiDescPresenter;
import com.example.administrator.newlooklook.presenter.implView.IWangyiDescActivity;
import com.example.administrator.newlooklook.util.NewsJsonUtils;
import com.example.administrator.newlooklook.util.OkHttpUtil;
import com.example.administrator.newlooklook.util.Urls;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/26.
 */

public class WangyiDescPresenterImpl extends BasePrensenterImpl implements IWangyiDescPresenter {
    IWangyiDescActivity iWangyiDescActivity;
    public WangyiDescPresenterImpl(IWangyiDescActivity iWangyiDescActivity){
        this.iWangyiDescActivity=iWangyiDescActivity;
    }

    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(Urls.NEW_DETAIL);
        sb.append(docId).append(Urls.END_DETAIL_URL);
        return sb.toString();
    }
    @Override
    public void getDescMessage(final String docid) {
        iWangyiDescActivity.showProgressDialog();
        String url = getDetailUrl(docid);
        OkHttpUtil.ResultCallback<String> loadNewsCallback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                NewsDetailBean newsDetailBean = NewsJsonUtils.readJsonNewsDetailBeans(response, docid);
                iWangyiDescActivity.updateDescmessage(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        };
        OkHttpUtil.get(url, loadNewsCallback);

    }
}
