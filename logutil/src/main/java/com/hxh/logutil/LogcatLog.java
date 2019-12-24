package com.hxh.logutil;

import android.content.Context;
import android.util.Log;

public class LogcatLog implements ILog {
    private final Context context;
    public LogcatLog(Context context){
        this.context = context;
    }
    @Override
    public void log(String content) {
        Log.e("LogcatLog", content);
    }
}
