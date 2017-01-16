package com.sys.blackcat.download.downloader;

/**
 * Created by yangcai on 17-1-14.
 */


public interface DownloadListener {
    void downErr();

    void downProcess(float process);//1 - 100

    void downDone();//下载完成
}



