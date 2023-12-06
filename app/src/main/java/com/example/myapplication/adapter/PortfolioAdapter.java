package com.example.myapplication.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.model.asset.Asset;
import com.example.myapplication.model.portfolio.Portfolio;

import java.util.List;

public class PortfolioAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Portfolio> portfolios;

    public PortfolioAdapter(Context context, List<Portfolio> portfolios) {
        this.context = context;
        this.portfolios = portfolios;
    }

    @Override
    public int getGroupCount() {
        return portfolios.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return portfolios.get(i).getAssets().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return portfolios.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return portfolios.get(groupPosition).getAssets().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        Portfolio portfolio = (Portfolio) getGroup(groupPosition);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.portfolio_item, null);
        }


        TextView title = view.findViewById(R.id.portfolio_title);
        TextView count = view.findViewById(R.id.portfolio_count);

        title.setText(portfolio.getName());
        count.setText("count: " + portfolio.getAssets_count());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        Asset asset = (Asset) getChild(groupPosition, childPosition);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.asset_item, null);
        }
        TextView title = view.findViewById(R.id.asset_title);
        TextView ticker = view.findViewById(R.id.asset_ticker);
        TextView price = view.findViewById(R.id.asset_price);

        title.setText(asset.getName());
        ticker.setText(asset.getTicker());
        price.setText(asset.getPrice());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
