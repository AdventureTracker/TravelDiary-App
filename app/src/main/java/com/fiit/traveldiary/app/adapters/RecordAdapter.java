package com.fiit.traveldiary.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.fiit.traveldiary.app.R;
import com.fiit.traveldiary.app.models.Record;

import java.util.ArrayList;

/**
 * Created by Jakub Dubec on 05/05/16.
 */
public class RecordAdapter extends ArrayAdapter<Record> {

	public RecordAdapter(Context context, ArrayList<Record> records) {
		super(context, 0, records);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final Record record = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.record_item, parent, false);
		}

		TextView recDay = (TextView) convertView.findViewById(R.id.recDay);
		TextView recType = (TextView) convertView.findViewById(R.id.recType);

		recDay.setText(record.getDayAsString("dd.MM.yyyy"));
		recType.setText(record.getRecordType().toString());

		return convertView;

	}

}
