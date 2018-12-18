package com.example.shafiq.staremis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private int[] colourList;
    private int[] imageList;
    private String[] imageNameList;

    CustomAdapter(Context context,int[] colourList,int[] imageList,String[] imageNameList) {
        this.context = context;
        this.colourList = colourList;
        this.imageList = imageList;
        this.imageNameList = imageNameList;
    }

    @Override
    public int getCount() {
        return imageNameList.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.sample_grid_view,parent,false);
        }
        ImageView imageView = convertView.findViewById(R.id.imageViewSampleGridViewID);
        TextView textView = convertView.findViewById(R.id.textViewSampleGridViewID);
        ImageView imageViewBackground = convertView.findViewById(R.id.imageViewSampleGridViewBackgroundID);

        imageViewBackground.setImageResource(colourList[position]);
        imageView.setImageResource(imageList[position]);
        textView.setText(imageNameList[position]);
        return convertView;
    }
}
