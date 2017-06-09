package com.example.administrator.newlooklook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.newlooklook.R;
import com.wingsofts.dragphotoview.DragPhotoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;


/**
 * Created by Administrator on 2017/5/20.
 */

public class MeiziDescribeActivity extends AppCompatActivity{
    @BindView(R.id.photo_view)
    DragPhotoView photo;

    Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizidesc_layout);
        ButterKnife.bind(this);
        final String url=getIntent().getStringExtra("url");
        Glide.with(this)
                .load(url)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        bitmap = ((GlideBitmapDrawable)resource).getBitmap();
                        photo.setImageBitmap(bitmap);
                    }
                });

        photo.setOnExitListener(new DragPhotoView.OnExitListener() {
            @Override
            public void onExit(DragPhotoView dragPhotoView, float v, float v1, float v2, float v3) {
                finish();
            }
        });

        photo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final MaterialDialog materialDialog = new MaterialDialog(MeiziDescribeActivity.this)
                        .setMessage("是否保存图片")
                        .setCanceledOnTouchOutside(true);

                materialDialog.setPositiveButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                saveImageToGallery(MeiziDescribeActivity.this,bitmap);
                            }
                        }).start();

                        Toast.makeText(MeiziDescribeActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                        materialDialog.dismiss();
                    }
                })
                        .setNegativeButton("否", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                materialDialog.dismiss();
                            }
                        }).show();
                return true;
            }
        });
    }
    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "IU";
        File appDir = new File(storePath);

        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();
            //把文件插入到系统图库
      /*     MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);*/

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
