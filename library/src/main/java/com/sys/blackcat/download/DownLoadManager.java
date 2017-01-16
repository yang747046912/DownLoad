package com.sys.blackcat.download;

import android.content.Context;

import com.sys.blackcat.download.downloader.DownloadListener;
import com.sys.blackcat.download.downloader.FileDownloadTask;
import com.sys.blackcat.download.utils.Md5Utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by yangcai on 17-1-14.
 */

public class DownLoadManager {

    private static final String TAG = DownLoadManager.class.getSimpleName();

    private static Context context;

    private static DownLoadManager instance;

    private ConcurrentHashMap<String, DownInfo> downInfos;

    private ConcurrentHashMap<String, FutureTask> futureTasks;

    private ExecutorService executor;

    public static synchronized DownLoadManager getInstance() {
        if (instance == null) {
            instance = new DownLoadManager();
        }
        return instance;
    }

    public DownLoadManager() {
        downInfos = new ConcurrentHashMap<>();
        futureTasks = new ConcurrentHashMap<>();
        executor = Executors.newFixedThreadPool(2);
    }

    public static void init(Context appcontext) {
        context = appcontext;
    }

    /**
     * @param downUrl
     * @param savePath 必须保证存放路径的正确性
     * @param listener
     * @return
     */
    public boolean startDownload(String downUrl, String savePath, final DownloadListener listener) {
        String md5Url = Md5Utils.getMd5String(downUrl);
        if (downInfos.containsKey(md5Url)) {
            return false;
        } else {
            final DownInfo info = new DownInfo(context, downUrl, savePath);
            FutureTask futureTask = addTask(info, listener);
            executor.submit(futureTask);
            return true;
        }
    }

    public boolean startDownload(String downUrl, final DownloadListener listener) {
        String md5Url = Md5Utils.getMd5String(downUrl);
        if (downInfos.containsKey(md5Url)) {
            return false;
        } else {
            final DownInfo info = new DownInfo(context, downUrl);
            FutureTask futureTask = addTask(info, listener);
            executor.submit(futureTask);
            return true;
        }
    }

    private FutureTask addTask(DownInfo info, DownloadListener listener) {
        downInfos.put(info.getMd5Key(), info);
        FileDownloadTask task = new FileDownloadTask(info, warpDownListener(info.getMd5Key(), listener));
        FutureTask futureTask = new FutureTask(task, null);
        futureTasks.put(info.getMd5Key(), futureTask);
        return futureTask;
    }

    private void removeTask(DownInfo info) {
        downInfos.remove(info.getMd5Key());
        FutureTask futureTask = futureTasks.get(info.getMd5Key());
        futureTask.cancel(true);
        futureTasks.remove(info.getMd5Key());
    }

    /**
     *
     * @param downUrl
     * @param savePath 必须保证存放路径的正确性
     * @param listener
     */
    public void startDownloadForce(String downUrl, String savePath, final DownloadListener listener) {
        String md5Url = Md5Utils.getMd5String(downUrl);
        DownInfo info = downInfos.get(md5Url);
        if (info != null) {
            removeTask(info);
        }
        startDownload(downUrl, savePath, listener);
    }
    public void startDownloadForce(String downUrl, final DownloadListener listener) {
        String md5Url = Md5Utils.getMd5String(downUrl);
        DownInfo info = downInfos.get(md5Url);
        if (info != null) {
            removeTask(info);
        }
        startDownload(downUrl, listener);
    }

    private DownloadListener warpDownListener(final String key, final DownloadListener listener) {
        return new DownloadListener() {
            @Override
            public void downErr() {
                listener.downErr();
                downInfos.remove(key);
            }

            @Override
            public void downProcess(float process) {
                listener.downProcess(process);
                DownInfo info = downInfos.get(key);
                if (info != null) {
                    info.setProgress(process);
                }
            }

            @Override
            public void downDone() {
                listener.downDone();
                downInfos.remove(key);
            }
        };
    }

    public void shutDown() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
            instance = null;
        }
    }

}
