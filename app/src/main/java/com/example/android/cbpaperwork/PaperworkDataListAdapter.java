package com.example.android.cbpaperwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import data.PaperworkData;

/**
 * Created by mtwichel on 7/20/17.
 */

public class PaperworkDataListAdapter extends ArrayAdapter<PaperworkData> {

    private List<PaperworkData> dataList;

    public PaperworkDataListAdapter(Context context, int resource, List<PaperworkData> objects) {
        super(context, resource, objects);
        this.dataList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.paperwork_item, parent, false);
        }

        PaperworkData data = dataList.get(position);

        TextView dateText = convertView.findViewById(R.id.date);
        dateText.setText(data.getDateString() + "  -  " + data.getCloser(0) + " & " + data.getCloser(1));

        return convertView;
    }
}
