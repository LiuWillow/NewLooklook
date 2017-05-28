package com.example.administrator.newlooklook.presenter;

import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.util.Log;

import com.example.administrator.newlooklook.api.ApiManger;
import com.example.administrator.newlooklook.bean.Gank;
import com.example.administrator.newlooklook.presenter.implPresenter.IMeiziPresenter;
import com.example.administrator.newlooklook.presenter.implView.IMeiziFragment;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/19.
 */

public class MeiziPresenterImpl extends BasePrensenterImpl implements IMeiziPresenter {
    IMeiziFragment iMeiziFragment;
    public MeiziPresenterImpl(IMeiziFragment iMeiziFragment){
        this.iMeiziFragment=iMeiziFragment;
    }
    @Override
    public void getMeiziData(int t) {
        iMeiziFragment.showProgressDialog();
       Subscription subscription= ApiManger.getInstance().getGankService().getGank(t)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Gank>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iMeiziFragment.hideProgressDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Gank gank) {
                        iMeiziFragment.hideProgressDialog();
                        iMeiziFragment.updateMeiziData(gank.getResult());
                    }
                });
        addSubscription(subscription);
    }
}
