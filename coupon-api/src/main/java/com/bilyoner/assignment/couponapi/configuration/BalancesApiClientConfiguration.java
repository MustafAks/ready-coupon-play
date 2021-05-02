package com.bilyoner.assignment.couponapi.configuration;

import com.bilyoner.assignment.couponapi.client.BalancesApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.AllArgsConstructor;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BalancesApiClientConfiguration {


    @Bean
    public BalancesApiClient balancesApiClient(ObjectMapper objectMapper, CloseableHttpClient httpClient) {

        Feign.Builder builder = Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new ResponseEntityDecoder(new JacksonDecoder(objectMapper)))
                .client(new ApacheHttpClient(httpClient))
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL);

        return BalancesApiClient.getInstance("http://localhost:9090/", builder);
    }

}
