package com.hxiaohui.update_by_system;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import androidx.core.content.FileProvider;

/**
 * @author huangxiaohui
 * @date 2019-12-15
 */
public class ApkDownloadTask {
    private Activity activity;
    private String downloadAddress;
    private String updateInfo;

    public ApkDownloadTask(Activity activity, String downloadAddress, String updateInfo) {
        this.activity = activity;
        this.downloadAddress = downloadAddress;
        this.updateInfo = updateInfo;
    }

    public void execute()
    {
        if (TextUtils.isEmpty(downloadAddress)) return;
        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloaduri = Uri.parse(downloadAddress);
        String lastPathSegment = downloaduri.getLastPathSegment();
        DownloadManager.Request request = new DownloadManager.Request(downloaduri);
        request.setMimeType("application/vnd.android.package-archive");
        request.setTitle(activity.getResources().getString(R.string.app_name));
        request.setVisibleInDownloadsUi(true);
        File apkfile = new File(Environment.getExternalStorageDirectory(), "app.apk");
        if(apkfile.exists())
        {
            apkfile.delete();
        }
        request.setDestinationUri(Uri.parse("file://" + apkfile.getAbsolutePath()));
        downloadManager.enqueue(request);
    }
}
