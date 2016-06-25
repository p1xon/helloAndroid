package com.example.administrator.chabaike.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.beans.Info;
import com.example.administrator.httplib.BitmapRequest;
import com.example.administrator.httplib.HttpHelper;
import com.example.administrator.httplib.Request;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class InfoAdapter extends BaseAdapter{

    private List<Info> list;

    public InfoAdapter(List<Info> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.content_lv_item,null);
            vh = new ViewHolder();
            vh.iv_icon = (ImageView) convertView.findViewById(R.id.item_iv);
            vh.tv_desc = (TextView) convertView.findViewById(R.id.item_tv_desc);
            vh.tv_rcount = (TextView) convertView.findViewById(R.id.item_tv_rc);
            vh.tv_time = (TextView) convertView.findViewById(R.id.item_tv_time);
            convertView.setTag(vh);
        }
        Info info = (Info) getItem(position);
        vh = (ViewHolder) convertView.getTag();
        vh.tv_time.setText(""+info.getTime());
        vh.tv_desc.setText(info.getTitle());
        vh.tv_rcount.setText("阅读数："+info.getRcount());
        vh.iv_icon.setImageResource(R.drawable.ic_launcher);
        loadImage(vh.iv_icon,"http://tnfs.tngou.net/image"+info.getImg()+"_100x100");

        return convertView;
    }

    public class ViewHolder{

        public TextView tv_desc;
        public TextView tv_time;
        public TextView tv_rcount;
        public ImageView iv_icon;

    }
    private void loadImage(final ImageView iv_icon, final String url) {
        iv_icon.setTag(url);
        BitmapRequest br = new BitmapRequest(url, Request.Method.GET,new Request.Callback<Bitmap>(){

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(final Bitmap response) {
                if(iv_icon != null && response != null &(iv_icon.getTag()).equals(url)){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            iv_icon.setImageBitmap(response);
                        }
                    });
                }
            }
        });
        HttpHelper.addRequest(br);
    }
}
