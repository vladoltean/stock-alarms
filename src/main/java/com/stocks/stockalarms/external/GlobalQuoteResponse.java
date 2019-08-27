package com.stocks.stockalarms.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.ToString;

/**
 * By vlad.oltean on 2019-08-18.
 */
@JsonRootName("Global Quote")
@ToString
public class GlobalQuoteResponse {

    private String symbol;
    private String open;
    private String high;
    private String low;
    private String price;
    private String volume;
    private String latestTradingDay;
    private String previousClose;
    private String change;
    private String changePercent;


    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("01. symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    @JsonProperty("open")
    public String getOpen() {
        return open;
    }

    @JsonProperty("02. open")
    public void setOpen(String open) {
        this.open = open;
    }


    @JsonProperty("high")
    public String getHigh() {
        return high;
    }

    @JsonProperty("03. high")
    public void setHigh(String high) {
        this.high = high;
    }


    @JsonProperty("low")
    public String getLow() {
        return low;
    }

    @JsonProperty("04. low")
    public void setLow(String low) {
        this.low = low;
    }


    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("05. price")
    public void setPrice(String price) {
        this.price = price;
    }


    @JsonProperty("volume")
    public String getVolume() {
        return volume;
    }

    @JsonProperty("06. volume")
    public void setVolume(String volume) {
        this.volume = volume;
    }


    @JsonProperty("latestTradingDay")
    public String getLatestTradingDay() {
        return latestTradingDay;
    }

    @JsonProperty("07. latest trading day")
    public void setLatestTradingDay(String latestTradingDay) {
        this.latestTradingDay = latestTradingDay;
    }


    @JsonProperty("previousClose")
    public String getPreviousClose() {
        return previousClose;
    }

    @JsonProperty("08. previous close")
    public void setPreviousClose(String previousClose) {
        this.previousClose = previousClose;
    }


    @JsonProperty("change")
    public String getChange() {
        return change;
    }

    @JsonProperty("09. change")
    public void setChange(String change) {
        this.change = change;
    }


    @JsonProperty("changePercent")
    public String getChangePercent() {
        return changePercent;
    }

    @JsonProperty("10. change percent")
    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }
}
