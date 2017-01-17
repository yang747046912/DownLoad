package com.sys.blackcat.download.downloader;

/**
 * Created by yangcai on 17-1-14.
 */


public interface DownloadListener {
    void downloadFailed(String failureReason);

    void downloadProgress(float process,int current, int total);//1 - 100

    void downloadSuccess();//下载完成
}



