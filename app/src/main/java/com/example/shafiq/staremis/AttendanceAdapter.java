package com.example.shafiq.staremis;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AttendanceAdapter extends BaseAdapter {
    public String[] posArray = new String[100];
    private Context context;
    private ArrayList<String> studentRoll;
    private ArrayList<String> currentDay;
    private int roll;


    AttendanceAdapter(Context context, ArrayList<String> studentRoll, ArrayList<String> currentDay) {
        this.context = context;
        this.studentRoll = studentRoll;
        this.currentDay = currentDay;
    }

    @Override
    public int getCount() {
        return studentRoll.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.sample_grid_view_attendance, parent, false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView = convertView.findViewById(R.id.textViewSampleGridViewID);
        viewHolder.textView.setText(studentRoll.get(position));

        if (currentDay.get(position).equals("1")) {
            viewHolder.textView.setBackgroundColor(Color.parseColor("#98c08e"));//colour green
            posArray[position] = "0";
        } else if (currentDay.get(position).equals("0")) {
            viewHolder.textView.setBackgroundColor(Color.parseColor("#de7171"));//colour red
            posArray[position] = "1";
        }
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                roll = position + 1;

                if (posArray[position] == null) {
                    posArray[position] = "0";
                }
                if (posArray[position].equals("0")) {
                    viewHolder.textView.setBackgroundColor(Color.parseColor("#de7171"));//colour red
                    Toast.makeText(context, "Roll...." + roll + "  is absent", Toast.LENGTH_SHORT).show();
                    currentDay.set(position, "0");
                    posArray[position] = "1";
                    AttendanceAdapter.this.notifyDataSetChanged();
                } else if (posArray[position].equals("1")) {
                    viewHolder.textView.setBackgroundColor(Color.parseColor("#98c08e"));//colour green
                    Toast.makeText(context, "Roll...." + roll + "  is present", Toast.LENGTH_SHORT).show();
                    currentDay.set(position, "1");
                    posArray[position] = "0";
                    AttendanceAdapter.this.notifyDataSetChanged();
                }

            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView textView;

    }
}
