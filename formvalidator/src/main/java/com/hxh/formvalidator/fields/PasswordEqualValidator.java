package com.hxh.formvalidator.fields;

import android.text.TextUtils;
import android.widget.TextView;

import com.hxh.formvalidator.EditValidator;
import com.hxh.formvalidator.IFormValidator;

public class PasswordEqualValidator extends EditValidator implements IFormValidator {
    private TextView et1, et2;
    public PasswordEqualValidator(TextView et1, TextView et2) {
        super(et1);
        this.et1 = et1;
        this.et2 = et2;
    }

    @Override
    public boolean isEnable() {
        return !TextUtils.isEmpty(et1.getText().toString()) && !TextUtils.isEmpty(et2.getText().toString());
    }

    @Override
    public boolean isValid() {
        if(!et1.getText().toString().equals(et2.getText().toString()))
        {
            showToast("两次输入密码不一致！");
            return false;
        }
        return true;
    }
}
