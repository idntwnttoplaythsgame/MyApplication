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
import com.example.myapplication.model.asset.Asset;
import com.example.myapplication.model.news.News;

import java.util.ArrayList;

public class AssetAdapter extends ArrayAdapter<Asset> {
    public AssetAdapter(@NonNull Context context, ArrayList<Asset> assets) {
        super(context, R.layout.assert_item, assets);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Asset asset = getItem(position);

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.assert_item, parent, false);
        }

        TextView title = view.findViewById(R.id.asset_title);
        TextView ticker = view.findViewById(R.id.asset_ticker);
        TextView price = view.findViewById(R.id.asset_price);

//        image.setImageDrawable(news.getImage());
        title.setText(asset.getName());
        ticker.setText(asset.getTicker());
        price.setText(asset.getPrice());

        return view;
    }
}
