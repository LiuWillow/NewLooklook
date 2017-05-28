package com.example.administrator.newlooklook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.newlooklook.R;
import com.example.administrator.newlooklook.bean.NewsDetailBean;
import com.example.administrator.newlooklook.presenter.WangyiDescPresenterImpl;
import com.example.administrator.newlooklook.presenter.implView.IWangyiDescActivity;


import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/25.
 */

public class WangyiDescActivity extends AppCompatActivity implements IWangyiDescActivity{
    @BindView(R.id.wangyi_desc_image)
    ImageView descImage;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.wangyi_desc_title)
    TextView descTitle;
    @BindView(R.id.ht_newscontent)
    HtmlTextView htmlTextView;
    @BindView(R.id.nest)
    NestedScrollView nestedScrollView;
    private String id;
    private String url;
    private String title;
    private WangyiDescPresenterImpl wangyiDescPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wangyi_desc_layout);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("docid");
        url=getIntent().getStringExtra("img");
        title=getIntent().getStringExtra("title");
        Glide.with(this)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(descImage);
        descTitle.setText(title);
        wangyiDescPresenter=new WangyiDescPresenterImpl(this);
        wangyiDescPresenter.getDescMessage(id);
    }

    @Override
    public void updateDescmessage(NewsDetailBean newsDetailBean) {
        hideProgressDialog();
        htmlTextView.setHtmlFromString(newsDetailBean.getBody(), new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
