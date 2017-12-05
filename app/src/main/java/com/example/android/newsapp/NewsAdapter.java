package com.example.android.newsapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> earthquakes) {

        super(context, 0, earthquakes);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder;


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.display_view, parent, false);
            holder = new ViewHolder();
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
            holder.iv = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        News currentNews = getItem(position);


        holder.tv1.setText(currentNews.getTitle());
        holder.tv2.setText(currentNews.getType());

        String imageurl = currentNews.getThumbnail();
        Picasso.with(getContext()).load(imageurl).into(holder.iv);

        return convertView;
    }

}

