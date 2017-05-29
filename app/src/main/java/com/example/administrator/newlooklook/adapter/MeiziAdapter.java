package com.example.administrator.newlooklook.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.tv.TvContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.newlooklook.R;
import com.example.administrator.newlooklook.activity.MeiziDescribeActivity;
import com.example.administrator.newlooklook.bean.Meizi;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/19.
 */

public class MeiziAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Meizi> data=new ArrayList<>();
    private static final int TYPE_NORMAL=1;
    private static final int TYPE_MORE=-1;
    private Context mContext;
    private boolean loadingMore;

    public MeiziAdapter(Context context){
        mContext=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_NORMAL:
                return new MeiziHolder(LayoutInflater.from(mContext).inflate(R.layout.meizifragment_item,parent,false));
            case TYPE_MORE:
                return new LoadingMoreHolder(LayoutInflater.from(mContext).inflate(R.layout.loading_layout, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case TYPE_NORMAL:
                bindNormal((MeiziHolder)holder);
                break;
            case TYPE_MORE:
                bindMore((LoadingMoreHolder)holder);
                break;
        }

    }
    private void bindNormal(MeiziHolder holder){
        final Meizi meizi=data.get(holder.getAdapterPosition());
        Glide.with(mContext)
                .load(meizi.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(holder.imageView);
        holder.textView.setText(meizi.getDesc());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,MeiziDescribeActivity.class);
                intent.putExtra("url",meizi.getUrl());
                mContext.startActivity(intent);
            }
        });

    }
    private void bindMore(LoadingMoreHolder holder){
        holder.loadingBar.setVisibility(loadingMore ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position){
        if (position < getDataItemCount() && getDataItemCount()> 0) {
            return TYPE_NORMAL;
        }
        return TYPE_MORE;
    }

    private int getDataItemCount() {
        return data.size();
    }




    private class MeiziHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MeiziHolder(View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.meizi_image_item);
            textView=(TextView)itemView.findViewById(R.id.desc_tv);
        }
    }

    public static class LoadingMoreHolder extends RecyclerView.ViewHolder{
        ProgressBar loadingBar;
        public LoadingMoreHolder(View itemView) {
            super(itemView);
            loadingBar=(ProgressBar)itemView;
        }
    }

    public void addItems(ArrayList<Meizi> meizis){
        data.addAll(meizis);
        notifyDataSetChanged();
    }

    private int getLoadingMoreItemPosition() {
        return loadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    public void loadingStart() {
        if (loadingMore) return;
        loadingMore = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    public void loadingfinish() {
        if (!loadingMore) return;
        final int loadingPos = getLoadingMoreItemPosition();
        loadingMore = false;
        notifyItemRemoved(loadingPos);
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

}

