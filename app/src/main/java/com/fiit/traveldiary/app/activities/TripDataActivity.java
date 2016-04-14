package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class TripDataActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_data);

        findViewById(R.id.save).setOnClickListener(this);
    }

    private EditText getName(){
        return (EditText) findViewById(R.id.name);
    }

    private EditText getDestination(){
        return (EditText) findViewById(R.id.destination);
    }

    private EditText getStartDate(){
        return (EditText) findViewById(R.id.startDate);
    }

    private EditText getEstimatedArrival(){
        return (EditText) findViewById(R.id.estimatedArrival);
    }

    private EditText getDecsription(){
        return (EditText) findViewById(R.id.decsription);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                //TODO implement
                break;
        }
    }
}
