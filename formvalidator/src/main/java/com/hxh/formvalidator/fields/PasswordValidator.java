package com.hxh.formvalidator.fields;

import android.widget.TextView;

import com.hxh.formvalidator.EditValidator;
import com.hxh.formvalidator.IFormValidator;

import java.util.regex.Pattern;

public class PasswordValidator extends EditValidator implements IFormValidator {
    public static final String  REGEX = "^[\\S]{6,18}$";
    public PasswordValidator(TextView et) {
        super(et);
    }

    @Override
    public boolean isValid() {
        boolean result = Pattern.compile(REGEX).matcher(getEditString()).find();
        if (!result) {
            getEt().requestFocus();
            showToast("请输入6-18位密码");
            return false;
        }
        return true;
    }
}
