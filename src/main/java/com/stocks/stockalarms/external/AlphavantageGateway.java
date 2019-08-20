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
            GlobalQuoteResponse globalQuoteResponse = restTemplate.getForObject(quoteUrl, GlobalQuoteResponse.class);
            return globalQuoteResponse;
        } catch (RestClientException e){
            LOGGER.error("Error calling alphavantage API: ", e);
            throw e;
        }
    }


    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

        GlobalQuoteResponse resp = mapper.readValue(new File("/Users/vlad.oltean/java/stock-alarms/src/main/resources/test.json"), GlobalQuoteResponse.class);


        System.out.println(resp);
    }

}
