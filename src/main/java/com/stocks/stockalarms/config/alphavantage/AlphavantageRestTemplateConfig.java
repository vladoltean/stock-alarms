package com.stocks.stockalarms.config.alphavantage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocks.stockalarms.config.LoggingRequestInterceptor;

/**
 * By vlad.oltean on 2019-08-18.
 */
@Configuration
public class AlphavantageRestTemplateConfig {

    @Bean
    public RestTemplate alphavantageRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

        MappingJackson2HttpMessageConverter mapper = new MappingJackson2HttpMessageConverter();
        mapper.setObjectMapper(objectMapper);

        restTemplate.setMessageConverters(Arrays.asList(mapper));

        List<ClientHttpRequestInterceptor> interceptors = getClientHttpRequestInterceptors();
//        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

    private List<ClientHttpRequestInterceptor> getClientHttpRequestInterceptors() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        return interceptors;
    }

}