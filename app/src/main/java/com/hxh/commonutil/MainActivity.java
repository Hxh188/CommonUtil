package com.hxh.commonutil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hxiaohui.update_by_system.UpdateVersion;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://appdl.hicloud.com/dl/appdl/application/apk/42/42102c39679f443a8314bfd7329f4f61/com.ss.android.ugc.aweme.1912132012.apk?sign=portal@portal1576383198262&source=portalsite&maple=0&distOpEntity=HWSW";
        new UpdateVersion(this).update("有新版本", url);
    }
}
