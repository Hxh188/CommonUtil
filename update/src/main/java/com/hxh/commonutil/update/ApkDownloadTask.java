package com.hxh.commonutil.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * todo:兼容android 7.0 --->  xml 配置， Uri.fromFile 测试
 * 有时间需要做
 */
public class ApkDownloadTask extends AsyncTask<String, Integer, Boolean>
{
    private WeakReference<Activity> activityRef;
    private String downloadAdress;
    private String downloadAlert;
    public static final String DOWNLOAD_DIR = "Download/";
    private ProgressDialog progressDialog;

    public ApkDownloadTask(Activity activity, String downloadAdress, String downloadAlert)
    {
        activityRef = new WeakReference<>(activity);
        this.downloadAdress = downloadAdress;
        this.downloadAlert = downloadAlert;
    }

    /**
     * 显示下载对话框
     */
    private void showProgressDialog(String downloadAlert) {
        progressDialog = new ProgressDialog(activityRef.get());
        progressDialog.setTitle("下载更新中...");
        progressDialog.setMax(100);
        progressDialog.setMessage(downloadAlert);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog(downloadAlert);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean isSucceed = false;
        try {
            URL url = new URL(downloadAdress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            int fileSize = con.getContentLength();
            int downloadSize = 0;
            InputStream is = con.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DOWNLOAD_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
            File apkFile = new File(file, "parkpatrol_" + BuildConfig.FLAVOR + ".apk");
            FileOutputStream fos = new FileOutputStream(apkFile);
            byte buf[] = new byte[10240];
            int c;
            while ((c = is.read(buf)) > 0) {
                fos.write(buf, 0, c);
                downloadSize += c;
                publishProgress((int) (((downloadSize * 1.0)/fileSize) * 100));
            }
            fos.close();
            is.close();
            isSucceed = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSucceed;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if(s)
        {
            install();
        }else
        {
            Toast.makeText(activityRef.get(), "新版本app下载失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳到系统安装应用界面（新大陆开发调试样机跳不到安装界面，在正式机上是正常的）
     */
    private void install() {
        File apkfile = new File(Environment.getExternalStorageDirectory()+"/" + DOWNLOAD_DIR +"parkpatrol_" + BuildConfig.FLAVOR + ".apk");
        if (!apkfile.exists()) {
            Toast.makeText(activityRef.get(), "安装包文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        activityRef.get().startActivity(i);
    }
}
