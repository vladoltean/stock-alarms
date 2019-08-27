package com.stocks.stockalarms.external;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.stocks.stockalarms.config.alphavantage.AlphavantageProperties;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-18.
 */
@Component
@AllArgsConstructor
public class AlphavantageGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlphavantageGateway.class);

    private final RestTemplate unwrapRootValueRestTemplate;
    private final RestTemplate restTemplate;
    private final AlphavantageProperties alphavantageProperties;


    public GlobalQuoteResponse getMostRecentQuoteBySymbol(String symbol) {

        URI quoteUrl = alphavantageProperties.getQuoteUrl(symbol);

        try {
            return unwrapRootValueRestTemplate.getForObject(quoteUrl, GlobalQuoteResponse.class);
        } catch (RestClientException e) {
            LOGGER.error(String.format("Error calling alphavantage API: %s: ", quoteUrl.toString()), e);
            throw e;
        }
    }

    public SymbolSearchResponse search(String query) {
        URI url = alphavantageProperties.getSymbolSearchUrl(query);

        try {
            return restTemplate.getForObject(url, SymbolSearchResponse.class);
        } catch (RestClientException e) {
            LOGGER.error(String.format("Error calling alphavantage API: %s: ", url.toString()), e);
            throw e;
        }
    }

}
