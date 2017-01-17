package com.sys.blackcat.download.downloader;

import com.sys.blackcat.download.DownInfo;
import com.sys.blackcat.download.utils.IoUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

/**
 * Created by yangcai on 17/1/16.
 */

public class FileDownloadTask implements Runnable, IoUtils.CopyListener {

    private DownInfo info;

    private FileDownloader fileDownloader;

    private DownloadListener listener;

    public FileDownloadTask(DownInfo info, DownloadListener listener) {
        this.info = info;
        fileDownloader = new FileDownloader();
        this.listener = listener;
    }

    @Override
    public void onBytesCopied(int current, int total) {
        if (current == total) {
            listener.downloadProgress(100,current,total);
            listener.downloadSuccess();
        } else {
            listener.downloadProgress((current* 1f) / (total * 1f) * 100,current,total);
        }
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = fileDownloader.getStream(info.getDownUrl());
            save(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            listener.downloadFailed(e.toString());
        } finally {
            IoUtils.closeSilently(inputStream);
        }
    }

    private void save(InputStream inputStream) throws IOException {
        FileOutputStream out = new FileOutputStream(info.getSavePath());
        try {
            IoUtils.copyStream(inputStream, out, this, 1024);
        } catch (InterruptedIOException e) {
                e.printStackTrace();
        } catch (IOException e) {
            throw e;
        }
    }
}