package com.example.gallery_anna0410;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
        String src = imageArrayList.getName();
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.img);

        // Picasso is used for asynchronously downloading images from a network, resources or file system, caching and displaying them
        Picasso.get().load(src).into(imageView);
        return listItemView;
    }
}
