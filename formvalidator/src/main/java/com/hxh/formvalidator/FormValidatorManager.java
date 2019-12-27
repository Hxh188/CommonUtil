package com.hxh.formvalidator;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.ArrayList;

public class FormValidatorManager implements TextWatcher {
    private ArrayList<EditValidator> etValidators = new ArrayList<>();
    private TextView button;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public void setListener()
    {
        for(EditValidator ev:etValidators)
        {
            TextView tv = ev.getEt();
            if(tv != null)
            {
                ev.getEt().addTextChangedListener(this);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        execute();
    }

    public void execute()
    {
        boolean isEnable = true;
        for(int i = 0;i < etValidators.size();i++)
        {
            EditValidator ev = etValidators.get(i);
            if(!ev.isEnable())
            {
                isEnable = false;
                break;
            }
        }
        button.setEnabled(isEnable);
    }

    public boolean isValid()
    {
        boolean isValid = true;
        for(int i = 0;i < etValidators.size();i++)
        {
            IFormValidator ev = (IFormValidator) etValidators.get(i);
            if(!ev.isValid())
            {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public static class Builder
    {
        private ArrayList<EditValidator> etValidators = new ArrayList<>();
        private TextView button;

        public Builder add(EditValidator validator)
        {
            etValidators.add(validator);
            return this;
        }

        public Builder button(TextView button)
        {
            this.button = button;
            return this;
        }

        public FormValidatorManager build()
        {
            FormValidatorManager manager = new FormValidatorManager();
            manager.button = button;
            manager.etValidators = etValidators;
            manager.setListener();
            manager.execute();
            return manager;
        }
    }
}
