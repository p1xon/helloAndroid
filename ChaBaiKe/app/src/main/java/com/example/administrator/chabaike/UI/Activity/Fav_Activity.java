package com.example.administrator.chabaike.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.Utils.DBManager;
import com.example.administrator.chabaike.adapter.FavAdapter;
import com.example.administrator.chabaike.beans.Info;

import java.util.List;

public class Fav_Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView fav;
    private FavAdapter adapter;
    private DBManager manager;
    private List<Info> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_);
        setTitle("收藏夹");

        fav = (ListView) findViewById(R.id.list_fav);
        fav.setOnItemClickListener(this);
        manager = new DBManager(this);

        list = manager.query("Cha_Fav");
        if(list != null){
            adapter = new FavAdapter(list);
            fav.setAdapter(adapter);
        }else{
            Toast.makeText(this,"暂无数据",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("bean",list.get(position));
        startActivity(intent);
    }
}
