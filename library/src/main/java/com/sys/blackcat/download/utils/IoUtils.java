package com.sys.blackcat.download.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by yangcai on 17/1/16.
 */

public class IoUtils {

    public static final int DEFAULT_IMAGE_TOTAL_SIZE = 500 * 1024; // 500 Kb

    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
        }
    }

    public static boolean copyStream(InputStream is, OutputStream os, CopyListener listener, int bufferSize)
            throws IOException {
        int current = 0;
        int total = is.available();
        if (total <= 0) {
            total = DEFAULT_IMAGE_TOTAL_SIZE;
        }

        final byte[] bytes = new byte[bufferSize];
        int count;
        listener.onBytesCopied(current, total);
        while ((count = is.read(bytes, 0, bufferSize)) != -1) {
            shouldStopLoading();
            os.write(bytes, 0, count);
            current += count;
            listener.onBytesCopied(current, total);
        }
        os.flush();
        return true;
    }

    private static boolean shouldStopLoading() {
        return Thread.currentThread().isInterrupted();
    }

    public static interface CopyListener {
        void onBytesCopied(int current, int total);
    }
}
