package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import com.fiit.traveldiary.app.R;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class TripListActivity {

    public class TripsListActivity extends AppCompatActivity implements View.OnClickListener {

        private FrameLayout frameLayout;
        private ListView listView;
        Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.trips_list);

            toolbar = (Toolbar) findViewById(R.id.toolbar);

            // toolbar.setTitle(R.string.toolbarTitle);
            setSupportActionBar(toolbar);

            toolbar.setNavigationIcon(R.drawable.sipka_back);

            frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
            findViewById(R.id.circleButton).setOnClickListener(this);
            listView = (ListView) findViewById(R.id.listView);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.circleButton:
                    //TODO implement
                    break;
            }
        }
    }

}
