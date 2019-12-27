package com.hxh.commonutil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.hxh.commonutil.databinding.ActivityTestFormBinding;
import com.hxh.formvalidator.FormValidatorManager;
import com.hxh.formvalidator.fields.PasswordEqualValidator;
import com.hxh.formvalidator.fields.PasswordValidator;
import com.hxh.formvalidator.fields.PhoneValidator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestFormBinding bind = DataBindingUtil.setContentView(this, R.layout.activity_test_form);

        final FormValidatorManager manager = new FormValidatorManager();
        manager.bindButton(bind.btn);
        manager.add(new PhoneValidator(bind.etPhone));
        manager.add(new PasswordValidator(bind.etPassowrd));
        manager.add(new PasswordValidator(bind.etPassowrdSure));
        manager.add(new PasswordEqualValidator(bind.etPassowrd, bind.etPassowrdSure));
        manager.execute();

        bind.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!manager.isValid())
                {
                    return;
                }
            }
        });
    }
}
