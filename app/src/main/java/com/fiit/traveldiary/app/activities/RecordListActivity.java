package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;


public class RecordListActivity extends Activity implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        private ListView dayList;
        private FrameLayout frameLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.records_list);

            imageView = (ImageView) findViewById(R.id.imageView);
            textView = (TextView) findViewById(R.id.textView);
            dayList = (ListView) findViewById(R.id.dayList);
            frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
            findViewById(R.id.circleButton).setOnClickListener(this);
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
