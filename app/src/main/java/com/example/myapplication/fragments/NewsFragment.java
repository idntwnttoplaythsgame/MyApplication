package com.example.myapplication.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NewsAdapter;
import com.example.myapplication.model.news.News;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsFragment extends Fragment {
    ArrayList<News> news;
    NewsAdapter adapter;
    Handler handler = new Handler();
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ListView recyclerView = view.findViewById(R.id.list_view);
        initializeNews();
        new FetchData().start();
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

    private void initializeNews() {
        news = new ArrayList<>();
        adapter = new NewsAdapter(getActivity(), news);
    }

    public class FetchData extends Thread{

        String data = "";


        @Override
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {

                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Fetching data");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                }
            });

            try {
                URL url = new URL("http://10.0.2.2:8082/news");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                Log.d("before", "asass");
                while ((line = bufferedReader.readLine()) != null) {
                    data += line;
                }
                JSONArray news_json = new JSONArray(data);
                news.clear();
                for (int i = 0; i < news_json.length(); i++) {

                    JSONObject news1 = news_json.getJSONObject(i);
                    news.add(new News(news1.getString("urlToImage"),
                            news1.getString("title"),
                            news1.getString("description"),
                            news1.getString("publishedAt")));
                    Log.d("assaassaassa", news1.getString("title"));


                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {

                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    adapter.notifyDataSetChanged();

                }
            });

        }
    }
}