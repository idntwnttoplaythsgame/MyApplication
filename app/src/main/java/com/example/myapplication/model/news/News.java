package com.example.myapplication.model.news;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.example.myapplication.actions.ImageActions;

import java.net.URL;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
public class News {

    private String image;
    private String title;
    private String description;
    private String date;

}
