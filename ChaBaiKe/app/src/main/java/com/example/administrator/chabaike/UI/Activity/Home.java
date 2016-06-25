package com.example.administrator.chabaike.UI.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.UI.Fragment.ContentFragment;
import com.example.administrator.chabaike.beans.TabInfo;

public class Home extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private TabLayout mTabs;
    private ViewPager viewpager;
    private TabInfo[] tabs = new TabInfo[]{

            new TabInfo("民生热点", 1),
            new TabInfo("娱乐热点", 2),
            new TabInfo("财经热点", 3),
            new TabInfo("体育热点", 4),
            new TabInfo("教育热点", 5),
            new TabInfo("社会热点", 6),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTabs = (TabLayout) findViewById(R.id.home_class);
        viewpager = (ViewPager) findViewById(R.id.home_vp);
        viewpager.setAdapter(new ContentAdapter(getSupportFragmentManager()));
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabs.setupWithViewPager(viewpager);


    }

    public class ContentAdapter extends FragmentStatePagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ContentFragment cf = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", tabs[position].class_id);
            cf.setArguments(bundle);
            return cf;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabs[position].name;
        }
    }

}
