package com.hxiaohui.update_by_system;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;

public class UpdateVersion {
    private Activity activity;
    public UpdateVersion(Activity activity)
    {
        this.activity = activity;
    }

    public void update(final String updateInfo, final String downloadAddress) {
        if (TextUtils.isEmpty(downloadAddress)) {
            return;
        }
        AlertDialog.Builder build = new AlertDialog.Builder(activity);
        build.setTitle("发现新版本");
        build.setMessage(updateInfo);
        build.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ApkDownloadTask(activity, downloadAddress, updateInfo).execute();
            }
        });
        build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        build.create().show();

    }
}
