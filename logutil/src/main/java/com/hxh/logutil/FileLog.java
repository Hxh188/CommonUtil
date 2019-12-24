package com.hxh.logutil;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FileLog implements ILog {
    public static final String LOG_PREFIX = "myapp_log";
    private static SimpleDateFormat formatLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式
    private static SimpleDateFormat formatFile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式

    private final Context context;

    private String fileType = "";

    public FileLog(Context context, String fileType)
    {
        this.context = context;
        this.fileType = fileType;
    }

    @Override
    public void log(String content) {
        writeLogfile(content);
    }

    private void writeLogfile(String content) {
        Date now = new Date();
        String logDate = formatFile.format(now);
        String logFileName;
        if(TextUtils.isEmpty(fileType))
        {
            logFileName = String.format("%s_%s.txt", LOG_PREFIX, logDate);
        }else
        {
            logFileName = String.format("%s_%s_%s.txt", LOG_PREFIX, fileType, logDate);
        }
        File dir = context.getExternalFilesDir(null);
        File logFile = new File(dir, logFileName);
        if(!logFile.exists())
        {
            try {
                logFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
                showToast(context, "日志文件创建失败");
                return;
            }
        }

        String logTime = formatLog.format(now);
        FileWriter writer = null;
        try {
            writer = new FileWriter(logFile, true);
            writer.append(logTime).append("  ---- >>> ");
            writer.append(content);
            writer.append("\n");
            writer.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除指定天数之前的日志文件
     * @param context
     * @param beforeDay
     */
    public static void deleteBeforeFile(Context context, int beforeDay)
    {
        File dir = context.getExternalFilesDir(null);
        File[] files = dir.listFiles();
        Calendar current = Calendar.getInstance();
        for(File f:files)
        {
            if(!f.getName().startsWith(LOG_PREFIX))
            {
                continue;
            }
            Calendar modifyTime = Calendar.getInstance();
            modifyTime.setTimeInMillis(f.lastModified());
            modifyTime.add(Calendar.DAY_OF_MONTH, beforeDay);
            boolean isBeforeDay = current.after(modifyTime);
            if(isBeforeDay){
                f.delete();
            }
        }
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    private void showToast(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
