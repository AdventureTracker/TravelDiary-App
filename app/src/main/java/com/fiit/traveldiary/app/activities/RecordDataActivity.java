package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import com.fiit.traveldiary.app.R;

public class RecordDataActivity extends Activity implements View.OnClickListener {

    private Spinner recordType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_data);

        findViewById(R.id.selectPhoto).setOnClickListener(this);
        recordType = (Spinner) findViewById(R.id.recordType);
    }

    private EditText getDate(){
        return (EditText) findViewById(R.id.date);
    }

    private EditText getDescription(){
        return (EditText) findViewById(R.id.description);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectPhoto:
                //TODO implement
                break;
        }
    }
}
