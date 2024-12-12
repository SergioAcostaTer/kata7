package com.moneycalculator.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Rate {
    private boolean success;
    private String base;
    private Map<String, Double> rates;

    @SerializedName("timestamp")
    private long timestamp;

    @SerializedName("date")
    private String date;

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getBase() {
        return base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDate() {
        return date;
    }
}
