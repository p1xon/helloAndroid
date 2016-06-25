package com.example.administrator.chabaike.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.chabaike.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;

    private LinearLayout ll;

    private int[] imgid = {R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};

    private List<ImageView> list;

    private MyAdapter adapter;

    private int curindex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

        setContentView(R.layout.guide);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        initData();

        inidot();

        adapter = new MyAdapter(list);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ll.getChildAt(position).setEnabled(false);
                ll.getChildAt(curindex).setEnabled(true);
                curindex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(adapter);

    }

    private void initData() {
        // 定义一个布局并设置参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        // 初始化引导图片列表
        list = new ArrayList<ImageView>();
        for (int i = 0; i < imgid.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);

            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //加载图片资源
//            curindex = i;
            iv.setImageResource(imgid[i]);
            list.add(iv);

            if (i==imgid.length-1){
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GuideActivity.this,Home.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }

    }

    private void inidot() {

        ll = (LinearLayout) findViewById(R.id.layout_dot);

        for (int i = 0;i < imgid.length;i++){
            ImageView dot = new ImageView(this);
            dot.setImageResource(R.drawable.guide_pager_selector);
            dot.setOnClickListener(this);
            dot.setTag(i);
            /*LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dot.getLayoutParams();
            params.setMargins(10,0,0,0);
            dot.setLayoutParams(params);*/
            ll.addView(dot);
        }
        curindex = 0;
        ll.getChildAt(curindex).setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        viewPager.setCurrentItem((Integer) v.getTag());
    }

    class MyAdapter extends PagerAdapter {

        private List<ImageView> list;

        public MyAdapter(List<ImageView> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return imgid.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }
    }
}
