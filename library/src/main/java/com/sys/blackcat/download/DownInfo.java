package com.sys.blackcat.download;

import android.content.Context;
import android.text.TextUtils;

import com.sys.blackcat.download.utils.Md5Utils;

import java.io.File;

/**
 * Created by yangcai on 17-1-14.
 */

public class DownInfo {

    private static final String DEFAULT_SAVE_DIR = "down_infos";

    private String downUrl;

    private String savePath;

    private String md5Key;


    //当前下载的百分比
    private float progress = 0;


    public DownInfo(Context context, String downUrl) {
        this(context, downUrl, null);
    }


    public DownInfo(Context context, String downUrl, String savePath) {
        this.downUrl = downUrl;
        md5Key = Md5Utils.getMd5String(this.downUrl);
        if (TextUtils.isEmpty(savePath)) {
            File saveDir = context.getDir(DEFAULT_SAVE_DIR, Context.MODE_PRIVATE);
            this.savePath = saveDir + File.separator + md5Key;
        } else {
            this.savePath = savePath;
        }

    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public String getSavePath() {
        return savePath;
    }

    public String getMd5Key() {
        return md5Key;
    }

    @Override
    public String toString() {
        return "DownInfo{" +
                "downUrl='" + downUrl + '\'' +
                ", savePath='" + savePath + '\'' +
                ", md5Key='" + md5Key + '\'' +
                ", progress=" + progress +
                '}';
    }
}
