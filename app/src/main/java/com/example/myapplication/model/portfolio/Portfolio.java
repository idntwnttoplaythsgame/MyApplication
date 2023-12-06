package com.example.myapplication.model.portfolio;

import com.example.myapplication.model.asset.Asset;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Portfolio {
    private String name;
    private String assets_count;
    private List<Asset> assets = new ArrayList<>();

    public Portfolio(String name, String assets_count, List<Asset> assets) {
        this.name = name;
        this.assets_count = assets_count;
        this.assets.addAll(assets);
    }
}
