package com.example.administrator.newlooklook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    //TODO wangyiadapter
    private Context mContext;
    private ArrayList<NewsBean> data=new ArrayList<>();
    public WangyiAdapter(Context context){
        mContext=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WangyiHolder(LayoutInflater.from(mContext).inflate(R.layout.wangyi_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindView((WangyiHolder)holder);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    private void bindView(WangyiHolder holder){
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
}
