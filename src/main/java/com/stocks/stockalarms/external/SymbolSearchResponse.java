package com.stocks.stockalarms.external;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * By vlad.oltean on 2019-08-27.
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymbolSearchResponse {

    private List<Item> bestMatches;

    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {

        @JsonProperty("1. symbol")
        private String symbol;

        @JsonProperty("2. name")
        private String name;

    }


}
