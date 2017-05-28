package com.example.administrator.newlooklook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.administrator.newlooklook.R;
import com.wingsofts.dragphotoview.DragPhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/5/20.
 */

public class MeiziDescribeActivity extends AppCompatActivity{
    @BindView(R.id.photo_view)
    DragPhotoView photo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizidesc_layout);
        ButterKnife.bind(this);
        final String url=getIntent().getStringExtra("url");
        Glide.with(this)
                .load(url)
                .into(photo);

        photo.setOnExitListener(new DragPhotoView.OnExitListener() {
            @Override
            public void onExit(DragPhotoView dragPhotoView, float v, float v1, float v2, float v3) {
                finish();
            }
        });
    }

}
