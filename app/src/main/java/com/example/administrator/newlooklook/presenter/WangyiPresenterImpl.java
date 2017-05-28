package com.example.administrator.newlooklook.presenter;

import com.example.administrator.newlooklook.api.ApiManger;
import com.example.administrator.newlooklook.bean.NewsList;
import com.example.administrator.newlooklook.presenter.implPresenter.IWangyiPresenter;
import com.example.administrator.newlooklook.presenter.implView.IWangyiFragment;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/24.
 */

public class WangyiPresenterImpl extends BasePrensenterImpl implements IWangyiPresenter {
    IWangyiFragment iWangyiFragment;
    public WangyiPresenterImpl(IWangyiFragment iWangyiFragment){
        this.iWangyiFragment=iWangyiFragment;
    }
    @Override
    public void getnews(int t) {
        iWangyiFragment.showProgressDialog();
        Subscription subscription= ApiManger.getInstance().getWangyiService().getNews(t)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsList newsList) {
                        iWangyiFragment.hideProgressDialog();
                        iWangyiFragment.updatenews(newsList.getNewsList());
                    }
                });
        addSubscription(subscription);
    }
}
