package com.stocks.stockalarms.config.alphavantage;

import java.net.URI;

import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import lombok.Getter;

/**
 * By vlad.oltean on 2019-08-18.
 */
@Configuration
@ConfigurationProperties(prefix = "alphavantage")
@Data
@Validated
public class AlphavantageProperties {

    private String apiToken;
    private String apiUrl;
    private String apiQuery;

    @Min(value = 1000, message = "Please provide a value larger than 1 second.")
    private int pollerRate;

    enum Functions {
        GLOBAL_QUOTE,
        TIME_SERIES_INTRADAY,
        SYMBOL_SEARCH
    }

    @Getter
    enum ParameterNames {
        FUNCTION("function"),
        SYMBOL("symbol"),
        KEYWORDS("keywords"),
        API_KEY("apikey"),
        INTERVAL("interval");

        ParameterNames(String value) {
            this.value = value;
        }

        private String value;
    }

    public URI getQuoteUrl(String symbol) {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(apiUrl)
                .path(apiQuery)
                .queryParam(ParameterNames.API_KEY.getValue(), apiToken)
                .queryParam(ParameterNames.FUNCTION.getValue(), Functions.GLOBAL_QUOTE.name())
                .queryParam(ParameterNames.SYMBOL.getValue(), symbol)
                .build()
                .toUri();
    }

    public URI getSymbolSearchUrl(String query) {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(apiUrl)
                .path(apiQuery)
                .queryParam(ParameterNames.API_KEY.getValue(), apiToken)
                .queryParam(ParameterNames.FUNCTION.getValue(), Functions.SYMBOL_SEARCH.name())
                .queryParam(ParameterNames.KEYWORDS.getValue(), query)
                .build()
                .toUri();
    }

}
