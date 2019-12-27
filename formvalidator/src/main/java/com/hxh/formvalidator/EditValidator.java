package com.hxh.formvalidator;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditValidator {
    private TextView et;
    public EditValidator(TextView et)
    {
        this.et = et;
    }

    public TextView getEt() {
        return et;
    }

    public boolean isEnable() {
        return !TextUtils.isEmpty(et.getText().toString());
    }

    public String getEditString()
    {
        return et.getText().toString();
    }

    protected void showToast(String str)
    {
        Toast.makeText(et.getContext(), str, Toast.LENGTH_SHORT).show();
    }
}
