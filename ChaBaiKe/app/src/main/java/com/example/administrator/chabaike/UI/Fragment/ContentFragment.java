package com.example.administrator.chabaike.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.UI.Activity.DetailActivity;
import com.example.administrator.chabaike.adapter.InfoAdapter;
import com.example.administrator.chabaike.beans.Info;
import com.example.administrator.httplib.HttpHelper;
import com.example.administrator.httplib.Request;
import com.example.administrator.httplib.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ContentFragment extends Fragment {

    private ListView mLv;
    private PtrClassicFrameLayout mRefreshView;

    private int class_Id;

    private InfoAdapter adapter;

    //private InfoListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.contentfragment_layout, null);
        initview(view);
        class_Id =getArguments().getInt("id");
        if (savedInstanceState != null){

            Info[] ins = (Info[]) savedInstanceState.getParcelableArray("cache");
            if (ins != null && ins.length != 0){
                infos = Arrays.asList(ins);
                adapter = new InfoAdapter(infos);
                mLv.setAdapter(adapter);
            }else {
                getDatafromNet();
            }
        }else {
            getDatafromNet();
        }

        return view;
    }

    private void initview(View view) {
        mLv = (ListView) view.findViewById(R.id.frag_content_lv);

        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),DetailActivity.class);
                intent.putExtra("bean",infos.get(position));
                startActivity(intent);
            }
        });

        mLv.setAdapter(adapter);

        mRefreshView = (PtrClassicFrameLayout) view.findViewById(R.id.content_ptr);
        mRefreshView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getDatafromNet();
            }
        });
    }

    private List<Info> infos = new ArrayList<>();

    private void getDatafromNet() {
        String url = "http://www.tngou.net/api/top/list?id=" + class_Id;
        Log.i("Home", url);

        StringRequest sr = new StringRequest(url, Request.Method.GET, new Request.Callback<String>() {

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    List<Info> list = jsonParser(jsonObject);

                    if (list != null) {
                        infos.clear();
                        infos.addAll(list);
                        System.out.println("取到新数据======");

                    }
                        if (adapter == null) {

                            adapter = new InfoAdapter(infos);
                            System.out.println("第一条的id"+infos.get(1).getId());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mLv.setAdapter(adapter);
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshView.refreshComplete();
                    }
                });
            }
        });
        HttpHelper.addRequest(sr);
    }

    private List jsonParser(JSONObject jsonObject) throws JSONException {

        if (jsonObject == null)return  null;
        JSONArray array = jsonObject.getJSONArray("tngou");
        if (array== null ||array.length() ==0)return null;

        List<Info> list = new ArrayList<>();
        int len = array.length();
        JSONObject obj = null;
        Info info =null;
        for (int i = 0; i <len ; i++) {
            obj = array.getJSONObject(i);
            info = new Info();
            info.setDescription(obj.optString("description"));
            info.setTitle(obj.optString("title"));
            Log.i("Home",obj.optString("description"));
            info.setRcount(obj.optInt("rcount"));
            long time = obj.getLong("time");
            String str = new SimpleDateFormat("yyyyMMdd:hhmmss").format(time);
            info.setTime(str);
            info.setImg(obj.optString("img"));
            info.setId(obj.optLong("id"));
            list.add(info);
            System.out.println("info的id:::"+obj.optInt("id"));
        }

        return list;
    }
    //fragment销毁的时候调用
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (infos == null || infos.size() == 0) return;
        Info[] parce = new Info[infos.size()];
        infos.toArray(parce);
        outState.putParcelableArray("cache", parce);
    }
}
