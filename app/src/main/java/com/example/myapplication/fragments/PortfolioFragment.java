package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NewsAdapter;
import com.example.myapplication.adapter.PortfolioAdapter;
import com.example.myapplication.model.asset.Asset;
import com.example.myapplication.model.news.News;
import com.example.myapplication.model.portfolio.Portfolio;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioFragment extends Fragment {

    ArrayList<Portfolio> portfolios;
    List<Asset> assets;
    PortfolioAdapter adapter;
    Handler handler = new Handler();
    ProgressDialog progressDialog;
    String portfolioName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);
        ExpandableListView expandableListView = view.findViewById(R.id.portfolio_list);
        initialize();
        new FetchData().start();
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        Button add_portfolio = view.findViewById(R.id.button);
        add_portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View dialogView = getLayoutInflater().inflate(R.layout.add_portfolio, null);
                builder.setView(dialogView);

                final EditText editTextPortfolioName = dialogView.findViewById(R.id.editText_portfolio_name);

                builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Получить название портфеля
                        portfolioName = editTextPortfolioName.getText().toString();

                        // Добавить портфель в список
                        String url = "http://10.0.2.2:8081/portfolio";
                        new CallAPI().execute(url, portfolioName);
                        // Обновить ExpandableListView
                    }
                });

                builder.setNegativeButton("Отмена", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    public class CallAPI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0]; // URL to call
            String data = params[1]; //data to post
            int responseCode;

            try {
                URL url = new URL(urlString + "?name=" + data);
            // Открытие соединения
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // Установка метода запроса на POST
                urlConnection.setRequestMethod("POST");
            // Получение потока вывода
                OutputStream outputStream = urlConnection.getOutputStream();

            // Получение кода ответа
                responseCode = urlConnection.getResponseCode();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Log.d("TAG", "Response Code: " + responseCode);

            return null;
        }
    }










    private void initialize() {
        portfolios = new ArrayList<>();
        assets = new ArrayList<>();
        adapter = new PortfolioAdapter(getActivity(), portfolios);
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
                URL url = new URL("http://10.0.2.2:8081/portfolio/portfolios");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                Log.d("before", "asass");

                while ((line = bufferedReader.readLine()) != null) {
                    data += line;
                }

                JSONArray portfolios_jsonArray = new JSONArray(data);

                portfolios.clear();
                for (int i = 0; i < portfolios_jsonArray.length(); i++) {

                    JSONObject portfoliosJson = portfolios_jsonArray.getJSONObject(i);

                    JSONArray assetsJsonArray = portfoliosJson.getJSONArray("assets");

                    assets.clear();
                    for (int j = 0; j < assetsJsonArray.length(); j++){
                        JSONObject assetsJson = assetsJsonArray.getJSONObject(j);
                        assets.add(new Asset("title",
                                assetsJson.getString("ticker"),
                                assetsJson.getString("price")));
                    }

                    portfolios.add(new Portfolio(portfoliosJson.getString("name"),
                            portfoliosJson.getString("assets_count"),
                            assets));
                    Log.d("assets", String.valueOf(portfolios.get(0).getAssets().size()));
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