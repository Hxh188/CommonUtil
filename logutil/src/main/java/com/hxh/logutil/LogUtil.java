package com.hxh.logutil;

import android.content.Context;

public class LogUtil {
    private LogcatLog logcatLog;
    private FileLog fileLog;

    private static volatile LogUtil instance;

    public static LogUtil getInstance(Context context) {
        if(instance == null)
        {
            synchronized (LogUtil.class)
            {
                if(instance == null)
                {
                    instance = new LogUtil(context);
                }
            }
        }
        return instance;
    }

    private LogUtil(Context context)
    {
        logcatLog = new LogcatLog(context.getApplicationContext());
        fileLog = new FileLog(context.getApplicationContext(), "");
    }



    public void logcat(String content)
    {
        logcatLog.log(content);
    }

    public void logfile(String content)
    {
        fileLog.setFileType("");
        fileLog.log(content);
    }

    public void logfile(String fileType, String content)
    {
        fileLog.setFileType(fileType);
        fileLog.log(content);
    }
}
