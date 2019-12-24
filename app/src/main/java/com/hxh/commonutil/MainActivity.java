package com.hxh.commonutil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hxh.logutil.FileLog;
import com.hxh.logutil.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FileLog.deleteBeforeFile(this, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        LogUtil.getInstance(this).logfile("test", "aaaaaaaaaaaaaaaaaa");
//        LogUtil.getInstance(this).logfile("aaaaaaaaaaaaaaaaaa");
    }
}
