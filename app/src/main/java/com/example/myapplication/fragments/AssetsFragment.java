package com.example.myapplication.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AssetAdapter;
import com.example.myapplication.adapter.NewsAdapter;
import com.example.myapplication.model.asset.Asset;
import com.example.myapplication.model.news.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AssetsFragment extends Fragment {

    ArrayList<Asset> assets;
    AssetAdapter adapter;
    Handler handler = new Handler();
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assets, container, false);
        ListView listView = view.findViewById(R.id.asset_list_view);
        initializeAssets();
        new FetchData().start();
        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

    private void initializeAssets() {
        assets = new ArrayList<>();
        adapter = new AssetAdapter(getActivity(), assets);
    }

    public class FetchData extends Thread{

        String data = "";


        @Override
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Fetching data");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                }
            });

            try {
                URL url = new URL("http://10.0.2.2:8080/api/quotes/stocks/list_stocks");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                Log.d("before", "asass");
                while ((line = bufferedReader.readLine()) != null) {
                    data += line;
                }
                JSONObject dataJson = new JSONObject(data);
                JSONObject marketdata = dataJson.getJSONObject("marketdata");
                JSONArray asset_json = marketdata.getJSONArray("data");
                assets.clear();
                for (int i = 0; i < asset_json.length(); i++) {

                    JSONArray assetJsonJSONObject = asset_json.getJSONArray(i);
                    assets.add(new Asset("Title",
                            assetJsonJSONObject.get(0).toString(),
                            assetJsonJSONObject.get(1).toString()));

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