package com.sys.blackcat.download.demo;

import android.app.Application;

import com.sys.blackcat.download.DownLoadManager;

/**
 * Created by yangcai on 17-1-14.
 */

public class APP extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        DownLoadManager.init(getApplicationContext());
    }




}
