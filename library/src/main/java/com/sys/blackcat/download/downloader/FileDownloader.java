package com.sys.blackcat.download.downloader;

import android.net.Uri;

import com.sys.blackcat.download.utils.IoUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yangcai on 17/1/16.
 */
public class FileDownloader implements BaseDownloader {
    protected static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    private final static int CONNECT_TIMEOUT = 3000;
    private final static int READ_TIMEOUT = 3000;
    private final static int MAX_REDIRECT_COUNT = 3;
    protected static final int BUFFER_SIZE = 32 * 1024; // 32 Kb

    @Override
    public InputStream getStream(String downUri) throws IOException {
        HttpURLConnection conn = createConnection(downUri);

        int redirectCount = 0;
        while (conn.getResponseCode() / 100 == 3 && redirectCount < MAX_REDIRECT_COUNT) {
            conn = createConnection(conn.getHeaderField("Location"));
            redirectCount++;
        }
        InputStream imageStream;
        try {
            imageStream = conn.getInputStream();
        } catch (IOException e) {
            throw e;
        }
        if (!shouldBeProcessed(conn)) {
            IoUtils.closeSilently(imageStream);
            throw new IOException("Image request failed with response code " + conn.getResponseCode());
        }
        return new ContentLengthInputStream(new BufferedInputStream(imageStream, BUFFER_SIZE), conn.getContentLength());
    }

    protected HttpURLConnection createConnection(String url) throws IOException {
        String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
        HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl).openConnection();
        conn.setConnectTimeout(CONNECT_TIMEOUT);
        conn.setReadTimeout(READ_TIMEOUT);
        return conn;
    }

    protected boolean shouldBeProcessed(HttpURLConnection conn) throws IOException {
        return conn.getResponseCode() == 200;
    }
}
