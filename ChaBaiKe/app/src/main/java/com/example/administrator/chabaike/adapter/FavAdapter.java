package com.example.administrator.chabaike.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.beans.Info;

import java.util.List;

/**
 * Created by Administrator on 2016/6/25.
 */
public class FavAdapter extends BaseAdapter{

    List<Info> list;

    public FavAdapter(List<Info> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHoder hoder = null;
        if(convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.item_fav,null);
            hoder = new ViewHoder();
            hoder.title = (TextView) convertView.findViewById(R.id.item_fav_title);
            convertView.setTag(hoder);
        }
        hoder = (ViewHoder) convertView.getTag();
        hoder.title.setText(list.get(position).getTitle());
        return convertView;
    }

    public class ViewHoder{
        public TextView title;
    }
}
