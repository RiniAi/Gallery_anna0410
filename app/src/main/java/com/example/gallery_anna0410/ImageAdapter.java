package com.example.gallery_anna0410;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter<Image> {

    public ImageAdapter(Activity context, ArrayList<Image> imageList) {
        super(context, 0, imageList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Image imageArrayList = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
        nameTextView.setText(imageArrayList.getName());

//        ImageView iconView = (ImageView) listItemView.findViewById(R.id.img);
//        if (imageArrayList.hasImage()) {
//            iconView.setImageResource(imageArrayList.getImageResourceId());
//            iconView.setVisibility(View.VISIBLE);
//
//        } else {
//            iconView.setVisibility(View.GONE);
//
//        }
        return listItemView;
    }
}
