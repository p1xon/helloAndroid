package com.example.administrator.chabaike.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.Utils.DBManager;
import com.example.administrator.chabaike.beans.Info;

public class DetailActivity extends AppCompatActivity {
    private WebView wb;
    private Info info;
    private DBManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("热点详情");

        Intent intent = getIntent();
        info = intent.getParcelableExtra("bean");
       /* long id = intent.getLongExtra("id", 0);
        System.out.println(""+id);*/
        wb = (WebView) findViewById(R.id.web_detail);
        wb.loadUrl("http://www.tngou.net/top/show/" + info.getId());
        manager = new DBManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        ShareActionProvider sp = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, info.getDescription() + "http://www.tngou.net/top/show/" + info.getId());
        sp.setShareIntent(shareIntent);

        menu.findItem(R.id.action_more).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(DetailActivity.this, MoreActivity.class);
                startActivity(intent);
                return false;
            }
        });

        menu.findItem(R.id.action_fav).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                manager.add(info,"Cha_Fav");
                Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}
