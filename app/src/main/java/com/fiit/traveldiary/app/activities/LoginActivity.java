package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import com.fiit.traveldiary.app.R;

public class LoginActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        findViewById(R.id.button).setOnClickListener(this);
    }

    private EditText getEditText(){
        return (EditText) findViewById(R.id.editText);
    }

    private EditText getEditText2(){
        return (EditText) findViewById(R.id.editText2);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                //TODO implement
                break;
        }
    }
}