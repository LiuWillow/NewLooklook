package com.example.administrator.newlooklook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.administrator.newlooklook.R;
import com.example.administrator.newlooklook.adapter.WangyiAdapter;
import com.example.administrator.newlooklook.bean.NewsBean;
import com.example.administrator.newlooklook.presenter.WangyiPresenterImpl;
import com.example.administrator.newlooklook.presenter.implView.IWangyiFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/24.
 */

public class WangYiFragment extends Fragment implements IWangyiFragment{
    //TODO 网易Fragment
    @BindView(R.id.recycle_wangyi)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private WangyiAdapter adapter;
    private RecyclerView.OnScrollListener onScrollListener;
    private WangyiPresenterImpl wangyiPresenter;
    private LinearLayoutManager linearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.wangyi_fragment_layout,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        adapter=new WangyiAdapter(getContext());
        recyclerView.setAdapter(adapter);
        wangyiPresenter=new WangyiPresenterImpl(this);
        onScrollListener=new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //TODO 设置滚动监听器
            }
        };
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        wangyiPresenter.getnews(0);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updatenews(ArrayList<NewsBean> arrayList) {
        adapter.addItems(arrayList);
    }
}
