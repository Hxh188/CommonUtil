package com.hxh.formvalidator.fields;

import android.widget.TextView;

import com.hxh.formvalidator.EditValidator;
import com.hxh.formvalidator.IFormValidator;

import java.util.regex.Pattern;

public class PhoneValidator extends EditValidator implements IFormValidator {

    public static final String REGEX_MOBILE_EXACT = "^[0-9]{11}";

    public PhoneValidator(TextView et) {
        super(et);
    }


    @Override
    public boolean isValid() {
        boolean result = Pattern.compile(REGEX_MOBILE_EXACT).matcher(getEditString()).find();
        if (!result) {
            getEt().requestFocus();
            showToast("请输入正确的手机号码");
            return false;
        }
        return true;
    }
}
