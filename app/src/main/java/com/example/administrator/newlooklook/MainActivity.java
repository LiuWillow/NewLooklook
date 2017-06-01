package com.example.administrator.newlooklook;

import android.Manifest;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.newlooklook.activity.AboutActivity;
import com.example.administrator.newlooklook.fragment.MeiziFragment;
import com.example.administrator.newlooklook.fragment.WangYiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity{
    @BindView(R.id.nav)
    NavigationView nav;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;

    private Fragment currentFragment;
    private MenuItem currentMenuItem;

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener=new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.about:
                    goAboutActivity();
                    break;
                case R.id.open:
                    drawer.openDrawer(GravityCompat.END);
            }
            return false;
        }
    };

    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener=new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.meizi_item:
                    switchFragment(new MeiziFragment());
                    drawer.closeDrawer(GravityCompat.END);
                    break;
                case R.id.wangyi_item:
                    switchFragment(new WangYiFragment());
                    drawer.closeDrawer(GravityCompat.END);
                    break;
            }
            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|      //全屏
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);       //设置状态栏透明

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        nav.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
        switchFragment(new MeiziFragment());
        if(currentFragment instanceof MeiziFragment){
            currentMenuItem=nav.getMenu().findItem(R.id.meizi_item);
            currentMenuItem.setChecked(true);
        }else if(currentFragment instanceof WangYiFragment){
            currentMenuItem=nav.getMenu().findItem(R.id.wangyi_item);
            currentMenuItem.setChecked(true);
        }
        int[][] state = new int[][]{
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_checked}  // pressed
        };

        int[] color = new int[]{
                Color.BLACK, Color.BLACK};
        int[] iconcolor = new int[]{
                Color.GRAY, Color.BLACK};
        nav.setItemTextColor(new ColorStateList(state, color));
        nav.setItemIconTintList(new ColorStateList(state, iconcolor));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_menu,menu);
        return true;
    }

    private void switchFragment(Fragment fragment){
        if(currentFragment==null||currentFragment!=fragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.contanier,fragment).commit();
            currentFragment=fragment;
        }
    }

    private void goAboutActivity(){
        Intent intent=new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    private class PackageManager {
    }

  /*  private Fragment findMenuItemByFragment(Fragment fragment){

    }*/

}
