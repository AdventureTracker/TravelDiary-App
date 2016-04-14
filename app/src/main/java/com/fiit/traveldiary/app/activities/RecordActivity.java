package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.widget.GridView;
import com.fiit.traveldiary.app.R;

public class RecordActivity extends Activity  {

    private TextView description;
    private GridView galleryGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        description = (TextView) findViewById(R.id.description);
        galleryGridView = (GridView) findViewById(R.id.galleryGridView);
    }

}

