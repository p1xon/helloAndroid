package com.example.administrator.chabaike.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.chabaike.R;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        findViewById(R.id.btn_fav).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btn_fav:
                intent = new Intent(this,Fav_Activity.class);
                startActivity(intent);
                break;
        }
    }
}
