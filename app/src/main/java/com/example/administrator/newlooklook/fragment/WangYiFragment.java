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
import com.example.administrator.newlooklook.widget.WrapContentLinearLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/24.
 */

public class WangYiFragment extends Fragment implements IWangyiFragment{
    @BindView(R.id.recycle_wangyi)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private WangyiAdapter adapter;
    private RecyclerView.OnScrollListener onScrollListener;
    private WangyiPresenterImpl wangyiPresenter;
    private WrapContentLinearLayoutManager linearLayoutManager;
    private boolean isLoading;
    private int index = 0;
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
                int pastVisiblesItems=linearLayoutManager.findFirstVisibleItemPosition();
                int totalItemCount=linearLayoutManager.getItemCount();
                int visibleItemCount=linearLayoutManager.getChildCount();
                if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    isLoading = true;
                    index += 20;
                    loadMoreDate();
                }
            }
        };
        linearLayoutManager=new WrapContentLinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(onScrollListener);
        loadDate();
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadDate() {
        if (adapter.getItemCount() > 0) {
            adapter.clearData();
        }
        wangyiPresenter.getnews(index);

    }
    private void loadMoreDate() {
        adapter.loadingStart();
        wangyiPresenter.getnews(index);
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
        adapter.loadingfinish();
        isLoading = false;
        adapter.addItems(arrayList);
    }
}
