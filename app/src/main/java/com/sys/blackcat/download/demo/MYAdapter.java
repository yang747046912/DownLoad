package com.sys.blackcat.download.demo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.sys.blackcat.download.DownLoadManager;
import com.sys.blackcat.download.demo.databinding.ActivityMainBinding;
import com.sys.blackcat.download.downloader.DownloadListener;

import java.io.File;


/**
 * Created by yangcai on 17-1-14.
 */

public class MYAdapter extends BaseAdapter {
    String[] BIG_FILE_URLS = {
            // 5m
            "http://mirror.internode.on.net/pub/test/5meg.test5",
            // 6m
            "http://download.chinaunix.net/down.php?id=10608&ResourceID=5267&site=1",
            // 8m
            "http://7xjww9.com1.z0.glb.clouddn.com/Hopetoun_falls.jpg",
            // 10m
            "http://dg.101.hk/1.rar",
            // 342m
            "http://180.153.105.144/dd.myapp.com/16891/E2F3DEBB12A049ED921C6257C5E9FB11.apk",
//            "http://mirror.internode.on.net/pub/test/5meg.test4",
//            "http://mirror.internode.on.net/pub/test/5meg.test3",
//            "http://mirror.internode.on.net/pub/test/5meg.test2",
//            "http://mirror.internode.on.net/pub/test/5meg.test1",
            // 6.8m
//            "http://dlsw.baidu.com/sw-search-sp/soft/7b/33461/freeime.1406862029.exe",
            // 10m
//            "http://mirror.internode.on.net/pub/test/10meg.test",
//            "http://mirror.internode.on.net/pub/test/10meg.test1",
//            "http://mirror.internode.on.net/pub/test/10meg.test2",
//            "http://mirror.internode.on.net/pub/test/10meg.test3",
            // 10m
            "http://mirror.internode.on.net/pub/test/10meg.test4",
//            "http://mirror.internode.on.net/pub/test/10meg.test5",
            // 20m
            "http://www.pc6.com/down.asp?id=72873",
            // 22m
            "http://113.207.16.84/dd.myapp.com/16891/2E53C25B6BC55D3330AB85A1B7B57485.apk?mkey=5630b43973f537cf&f=cf87&fsname=com.htshuo.htsg_3.0.1_49.apk&asr=02f1&p=.apk",
            // 206m
            "http://down.tech.sina.com.cn/download/d_load.php?d_id=49535&down_id=1&ip=42.81.45.159"
    };

    @Override
    public int getCount() {
        return BIG_FILE_URLS.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final VIewHOld hOld;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
            hOld = new VIewHOld(convertView);
            convertView.setTag(hOld);
        } else {
            hOld = (VIewHOld) convertView.getTag();
        }
        hOld.binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(position, hOld.binding.progress);
            }
        });

        return convertView;
    }

    private void start(int position, final ProgressBar view) {

//        DownLoadManager.getInstance().startDownload(BIG_FILE_URLS[position],
//                new DownloadListener() {
//                    @Override
//                    public void downloadFailed() {
//                        Log.d("---->", "downloadFailed");
//                    }
//
//                    @Override
//                    public void downloadProgress(final float process) {
//                        Log.d("---->", "downloadProgress " + process);
//                        view.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                view.setProgress((int) process);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void downloadSuccess() {
//                        Log.d("---->", "downloadSuccess");
//                    }
//                });

        File file = view.getContext().getDir("aaaaaaaa", Context.MODE_PRIVATE);

        DownLoadManager.getInstance().startDownloadForce(BIG_FILE_URLS[position], file.getAbsolutePath() + "/" + System.currentTimeMillis() / 1000,
                new DownloadListener() {
                    @Override
                    public void downloadFailed(String failureReason) {
                        Log.d("---->", "downloadFailed " + failureReason);
                    }

                    @Override
                    public void downloadProgress(final float process) {
                        Log.d("---->", "downloadProgress " + process);
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                view.setProgress((int) process);
                            }
                        });
                    }

                    @Override
                    public void downloadSuccess() {
                        Log.d("---->", "downloadSuccess");
                    }
                });


    }

    class VIewHOld {
        public ActivityMainBinding binding;

        public VIewHOld(View itemView) {
            binding = ActivityMainBinding.bind(itemView);
        }
    }
}
