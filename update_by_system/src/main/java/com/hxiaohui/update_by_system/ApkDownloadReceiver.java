package com.hxiaohui.update_by_system;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;

import androidx.core.content.FileProvider;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 升级文件下载完成广播
 * Created by Du JC on 2015/11/19.
 */
public class ApkDownloadReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, final Intent intent) {
		if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
			//获取文件下载路径
			File apkfile = new File(Environment.getExternalStorageDirectory(), "app.apk");
			//如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
			if (apkfile != null && apkfile.exists()) {
				Intent install = new Intent(Intent.ACTION_VIEW);
				install.addFlags(FLAG_ACTIVITY_NEW_TASK);//不会安装一半跳掉
				install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				Uri uri = null;
				if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
				{
					uri = Uri.fromFile(apkfile);
				}else
				{
					String name = context.getPackageName() + ".provider";
					uri = FileProvider.getUriForFile(context, name, apkfile);
				}
				install.setDataAndType(uri, "application/vnd.android.package-archive");
				try {
					context.startActivity(install);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
			try {
				Intent intent1 = new Intent();
				intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
				intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				intent1.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
				context.startActivity(intent1);
			} catch (Exception e) {
			}
		}
	}
}
