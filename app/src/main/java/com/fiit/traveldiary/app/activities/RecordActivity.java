package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.widget.GridView;
import com.fiit.traveldiary.app.R;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class RecordActivity extends AppCompatActivity  {

    private TextView description;
    private GridView galleryGridView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

       // toolbar.setTitle(R.string.toolbarTitle);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.sipka_back);

        description = (TextView) findViewById(R.id.description);
        galleryGridView = (GridView) findViewById(R.id.galleryGridView);
    }

}

