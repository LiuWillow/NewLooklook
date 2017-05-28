package com.example.administrator.newlooklook.presenter;

import com.example.administrator.newlooklook.presenter.implPresenter.IBasePresenter;
import com.example.administrator.newlooklook.presenter.implView.IBaseFragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/5/19.
 */

public class BasePrensenterImpl implements IBasePresenter {

    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }


    @Override
    public void unsubscribe() {

        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
