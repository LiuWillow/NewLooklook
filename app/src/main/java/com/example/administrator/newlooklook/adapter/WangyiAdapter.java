package com.example.administrator.newlooklook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.newlooklook.R;
import com.example.administrator.newlooklook.activity.WangyiDescActivity;
import com.example.administrator.newlooklook.bean.NewsBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/24.
 */

public class WangyiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private ArrayList<NewsBean> data=new ArrayList<>();

    private boolean loadingMore;
    private static final int TYPE_NORMAL=1;
    private static final int TYPE_MORE=-1;
    public WangyiAdapter(Context context){
        mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_NORMAL:
                return new WangyiHolder(LayoutInflater.from(mContext).inflate(R.layout.wangyi_item_layout,parent,false));
            case TYPE_MORE:
                return new LoadingMoreHolder(LayoutInflater.from(mContext).inflate(R.layout.wangyi_item_layout,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case TYPE_NORMAL:
                bindNormal((WangyiHolder)holder);
                break;
            case TYPE_MORE:
                bindMore((LoadingMoreHolder) holder);
                break;
        }
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
    @Override
    public int getItemCount() {
        return data.size();
    }
    private void bindNormal(WangyiHolder holder){
        final NewsBean newsBean=data.get(holder.getAdapterPosition());

        Glide.with(mContext)
                .load(newsBean.getImgsrc())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.image);

        holder.title.setText(newsBean.getTitle());
        holder.source.setText(newsBean.getSource());

        holder.wangyiLinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWangyiDescActivity(newsBean);
            }
        });
    }
    private void bindMore(LoadingMoreHolder holder){
        holder.loadingBar.setVisibility(loadingMore ? View.VISIBLE : View.INVISIBLE);
    }
    private class WangyiHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        TextView source;
        LinearLayout wangyiLinearlayout;
        public WangyiHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image_wangyi_item);
            title=(TextView)itemView.findViewById(R.id.title_tv);
            source=(TextView)itemView.findViewById(R.id.source_tv);
            wangyiLinearlayout=(LinearLayout)itemView.findViewById(R.id.wangyi_linearlayout);

        }
    }
    public static class LoadingMoreHolder extends RecyclerView.ViewHolder{
        ProgressBar loadingBar;
        public LoadingMoreHolder(View itemView) {
            super(itemView);
            loadingBar=(ProgressBar)itemView;
        }
    }

    public void addItems(ArrayList<NewsBean> arrayList){
        data.addAll(arrayList);
        notifyDataSetChanged();
    }

    private void startWangyiDescActivity(NewsBean newsBean){
        Intent intent=new Intent(mContext,WangyiDescActivity.class);
        intent.putExtra("title",newsBean.getTitle());
        intent.putExtra("img",newsBean.getImgsrc());
        intent.putExtra("docid",newsBean.getDocid());
        mContext.startActivity(intent);
    }
    public void clearData() {
        data.clear();
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
}
