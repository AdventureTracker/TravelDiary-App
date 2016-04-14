package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
public class TripListActivity {

    public class TripsListActivity extends Activity implements View.OnClickListener {

        private FrameLayout frameLayout;
        private ListView listView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.trips_list);

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
