package com.stocks.stockalarms.external.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import javax.swing.AbstractAction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stocks.stockalarms.external.AlphavantageGateway;
import com.stocks.stockalarms.external.GlobalQuoteResponse;
import com.stocks.stockalarms.external.SymbolSearchResponse;

/**
 * By vlad.oltean on 2019-08-27.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AlphavantageIT {

    @Autowired
    private AlphavantageGateway alphavantageGateway;

    @Test
    public void testGetMostRecentQuoteBySymbol() {
        String symbol = "AAPL";

        GlobalQuoteResponse globalQuoteResponse = alphavantageGateway.getMostRecentQuoteBySymbol(symbol);
        assertThat(globalQuoteResponse, is(notNullValue()));
    }

    @Test
    public void testSearch() {
        String query = "AAPL";

        SymbolSearchResponse response = alphavantageGateway.search(query);
        assertThat(response.getBestMatches().size(), is(greaterThan(1)));
    }

}
