package com.example.myapplication.actions;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

public class ImageActions {
    public static void LoadImageFromWebOperations(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .into(imageView);
    }
}
