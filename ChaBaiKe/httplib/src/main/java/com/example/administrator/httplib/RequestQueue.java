package com.example.administrator.httplib;

import android.util.Log;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 2016/6/23.
 */
public class RequestQueue {

    private BlockingDeque<Request> queue = new LinkedBlockingDeque<>();

    private final int MAX_THREADS = 3;

    private NetWorkDispatcher[] workers = new NetWorkDispatcher[MAX_THREADS];

    public RequestQueue(){
        Log.i("Dispatcher", "队列初始化");
        initDispatcher();

    }

    private void initDispatcher(){
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new NetWorkDispatcher(queue);
            workers[i].start();
        }
    }

    public void addRequest(Request request){
        queue.add(request);
    }


    private void  stop(){
        for (int i = 0; i < workers.length; i++) {
            workers[i].flag = false;
            workers[i].interrupt();
        }
    }

}
