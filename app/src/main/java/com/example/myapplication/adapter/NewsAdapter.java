package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.actions.ImageActions;
import com.example.myapplication.model.news.News;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, ArrayList<News> news) {
        super(context, R.layout.news_item, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        News news = getItem(position);

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        ImageView image = view.findViewById(R.id.image_view);
        TextView title = view.findViewById(R.id.news_title);
        TextView desc = view.findViewById(R.id.news_description);
        TextView date = view.findViewById(R.id.news_date);

//        image.setImageDrawable(news.getImage());
        ImageActions.LoadImageFromWebOperations(news.getImage(), image);
        title.setText(news.getTitle());
        desc.setText(news.getDescription());
        date.setText(news.getDate());

        return view;
    }
}
