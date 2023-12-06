package com.example.myapplication.model.asset;

import lombok.Data;

@Data
public class Asset {

    String name;
    String ticker;
    String price;

    public Asset(String name, String ticker, String price) {
        this.name = name;
        this.ticker = ticker;
        this.price = price;
    }
}
