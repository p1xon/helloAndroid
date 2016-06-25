package com.example.administrator.chabaike.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.chabaike.R;

public class LoadingActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preferences = getSharedPreferences("count", Context.MODE_PRIVATE);
                int count = preferences.getInt("count", 0);

                if (count == 0) {
                    Intent intent = new Intent(LoadingActivity.this,
                            GuideActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LoadingActivity.this,
                            Home.class); // 从启动动画ui跳转到主ui
                    startActivity(intent);

                    LoadingActivity.this.finish(); // 结束启动动画界面
                }
                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt("count", ++count);

                editor.commit();
            }

        }, 3000);
    }
}
