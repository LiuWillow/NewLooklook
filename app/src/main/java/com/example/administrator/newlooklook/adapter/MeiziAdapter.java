package com.example.administrator.newlooklook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public MeiziAdapter(Context context){
        mContext=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_NORMAL:
                return new MeiziHolder(LayoutInflater.from(mContext).inflate(R.layout.meizifragment_item,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    bindNormal((MeiziHolder)holder,position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position){
        if (position < data.size() && data.size() > 0) {
            return TYPE_NORMAL;
        }
        return TYPE_MORE;
    }


    private void bindNormal(MeiziHolder holder,int position){
        final Meizi meizi=data.get(position);
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


    private class MeiziHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MeiziHolder(View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.meizi_image_item);
            textView=(TextView)itemView.findViewById(R.id.desc_tv);
        }
    }

    public void addItems(ArrayList<Meizi> meizis){
        if(data==null){
            data=new ArrayList<>();
        }
        data.addAll(meizis);
        notifyDataSetChanged();
    }
}
