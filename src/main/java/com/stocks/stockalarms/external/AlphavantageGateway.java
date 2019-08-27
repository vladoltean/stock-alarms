package com.stocks.stockalarms.external;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocks.stockalarms.config.alphavantage.AlphavantageProperties;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-18.
 */
@Component
@AllArgsConstructor
public class AlphavantageGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlphavantageGateway.class);

    private final RestTemplate restTemplate;
    private final AlphavantageProperties alphavantageProperties;


    public GlobalQuoteResponse getMostRecentQuoteBySymbol(String symbol) {

        URI quoteUrl = alphavantageProperties.getQuoteUrl(symbol);

        try{
            return restTemplate.getForObject(quoteUrl, GlobalQuoteResponse.class);
        } catch (RestClientException e){
            LOGGER.error(String.format("Error calling alphavantage API for symbol %s: ", symbol), e);
            throw e;
        }
    }

}
