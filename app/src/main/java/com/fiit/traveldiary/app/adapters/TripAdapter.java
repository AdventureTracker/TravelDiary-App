package com.fiit.traveldiary.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.fiit.traveldiary.app.R;
import com.fiit.traveldiary.app.models.Trip;

import java.util.ArrayList;

/**
 * Created by Jakub Dubec on 04/05/16.
 */
public class TripAdapter extends ArrayAdapter<Trip> {

	public TripAdapter (Context context, ArrayList<Trip> trips) {
		super(context, 0, trips);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final Trip trip = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_item, parent, false);
		}

		TextView trpName = (TextView) convertView.findViewById(R.id.trpName);
		TextView trpStatus = (TextView) convertView.findViewById(R.id.trpStatus);

		trpName.setText(trip.getName());
		trpStatus.setText(trip.getStatus().toString());

		return convertView;

	}


}
