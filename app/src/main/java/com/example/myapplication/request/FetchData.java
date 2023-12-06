package com.example.myapplication.request;

import com.example.myapplication.model.news.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//public class FetchData extends Thread{
//
//    private URL url;
//    String data;
//    ArrayList<News> news;
//
//    public FetchData(String url ) throws MalformedURLException {
//        this.url = new URL(url);
//    }
//
//    @Override
//    public void run() {
//
//        try {
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = httpURLConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//
//            while ((line = bufferedReader.readLine()) != null) {
//                data += line;
//            }
//
//
//            if (!data.isEmpty()) {
//
//                JSONObject jsonObject = new JSONObject(data);
//                JSONArray news_json = jsonObject.getJSONArray("");
//
//                for (int i = 0; i < news_json.length(); i++) {
//
//                    JSONObject news = news_json.getJSONObject(i);
//                    News news_obj = new News(news.getString("urlToImage"), news.getString("title")
//                            , news.getString("description"), news.getString("publishedAt"));
//                    this.news.add(news_obj);
//
//
//                }
//
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}


