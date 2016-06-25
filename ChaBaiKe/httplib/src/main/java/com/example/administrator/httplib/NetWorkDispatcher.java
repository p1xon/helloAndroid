package com.example.administrator.httplib;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

/**
 * Created by Administrator on 2016/6/22.
 */
public class NetWorkDispatcher extends Thread {

    private BlockingDeque<Request> mqueue;

    public boolean flag = true;

    NetWorkDispatcher(BlockingDeque<Request> queue){
        mqueue = queue;
    }
    @Override
    public void run() {
        System.out.println("进入队列的线程========");
        while (flag && !isInterrupted()){
            try {
                //从请求队列中取出一个请求
                final Request req = mqueue.take();
                byte[] result = null;
                try {
                    result = getNetworkResponse(req);
                    if (result != null) {
                        req.dispatchContent(result);
                    }

                } catch (final Exception e) {
                    e.printStackTrace();
                    req.onError(e);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                flag = false;
            }
        }
    }

    public byte[] getNetworkResponse(Request request) throws Exception {
        if (TextUtils.isEmpty(request.getUrl())) {
            throw new Exception("url is null");
        }

        if (request.getMethod() == Request.Method.GET) {
            return getResponseByGET(request);
        }

        if (request.getMethod() == Request.Method.POST) {
            return getResponseByPOST(request);
        }
        return null;
    }

    /*
        GET
     */
    private byte[] getResponseByGET(Request req) throws Exception {
        URL url = new URL(req.getUrl());
        Log.i("Dispatcher",url.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        int code = conn.getResponseCode();

        if (code != 200) {
            throw new Exception("code eror");
        }
        InputStream is = conn.getInputStream();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[2048];
        while ((len = is.read(b)) != -1) {
            bao.write(b, 0, len);
        }
        is.close();
        return bao.toByteArray();
    }

    /*
        POST
     */
    private byte[] getResponseByPOST(Request req) throws Exception {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        url = new URL(req.getUrl());

        conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为post
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setDoOutput(true);
        String str = getPostEncodeString(req);
        byte[] content = null;
        if (str != null) {
            content = str.getBytes();
            conn.setRequestProperty("content-length", "" + content.length);
        }

        os = conn.getOutputStream();
        if (content != null) {
            os.write(content);
            os.flush();
        }

        int code = conn.getResponseCode();
        if (code != 200) {
            throw new Exception("code eror");
        }

        is = conn.getInputStream();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        int len = 0;
        byte[] b = new byte[2048];

        while ((len = is.read(b)) != -1) {
            bao.write(b, 0, len);
        }
        is.close();
        os.close();
        return bao.toByteArray();

    }

    public String getPostEncodeString(Request req) {
        HashMap<String, String> params = req.getPostParams();
        StringBuilder sb = new StringBuilder();
        if (params != null) {
            Iterator<HashMap.Entry<String, String>> iterator = params.entrySet().iterator();

            int i = 0;
            while (iterator.hasNext()) {
                if (i > 0) {
                    sb.append("&");
                }
                Map.Entry<String, String> value = iterator.next();
                String str = value.getKey() + "=" + value.getValue();
                sb.append(str);
                i++;
            }
            return sb.toString();
        }

        return null;
    }
}
