package com.sys.blackcat.download.downloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yangcai on 17/1/16.
 */

public interface BaseDownloader {

     InputStream getStream(String downUri) throws IOException;
}
