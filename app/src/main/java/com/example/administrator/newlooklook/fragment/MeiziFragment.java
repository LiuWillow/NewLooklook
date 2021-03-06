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
import com.example.administrator.newlooklook.adapter.MeiziAdapter;
import com.example.administrator.newlooklook.bean.Meizi;
import com.example.administrator.newlooklook.presenter.MeiziPresenterImpl;
import com.example.administrator.newlooklook.presenter.implView.IMeiziFragment;
import com.example.administrator.newlooklook.widget.WrapContentLinearLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/19.
 */

public class MeiziFragment extends Fragment implements IMeiziFragment{
    @BindView(R.id.recycle_meizi)
    RecyclerView recycle;
    @BindView(R.id.progress)
    ProgressBar progress;

    private int index=1;
    private MeiziAdapter adapter;
    private WrapContentLinearLayoutManager linearLayoutManager;
    private RecyclerView.OnScrollListener onScrollListener;
    private MeiziPresenterImpl mMeiziPresenter;
    private boolean isLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.meizi_fragment_layout,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mMeiziPresenter=new MeiziPresenterImpl(this);
        linearLayoutManager=new WrapContentLinearLayoutManager(getContext());
        recycle.setLayoutManager(linearLayoutManager);
        adapter=new MeiziAdapter(getContext());
        recycle.setAdapter(adapter);
        initListener();
        loadDate();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateMeiziData(ArrayList<Meizi> meizis) {
        adapter.loadingfinish();
        isLoading = false;
        adapter.addItems(meizis);
    }


    private void initListener(){

        onScrollListener=new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    int pastVisiblesItems=linearLayoutManager.findFirstVisibleItemPosition();
                    int totalItemCount=linearLayoutManager.getItemCount();
                    int visibleItemCount=linearLayoutManager.getChildCount();
                    if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        isLoading = true;
                        index += 1;
                        loadMoreDate();
                    }
                }
            }
        };

        recycle.addOnScrollListener(onScrollListener);

    }

    private void loadDate() {
        if (adapter.getItemCount() > 0) {
            adapter.clearData();
        }
        mMeiziPresenter.getMeiziData(index);

    }

    private void loadMoreDate() {
        adapter.loadingStart();
        mMeiziPresenter.getMeiziData(index);
    }

}
