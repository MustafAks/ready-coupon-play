package com.bilyoner.assignment.couponapi.client;

import com.bilyoner.assignment.couponapi.client.model.UpdateBalanceRequest;
import feign.Feign.Builder;

public class BalancesApiClient {

    private final static String API_PATH = "balances/";

    private volatile static BalancesApiClient instance;
    private final BalancesApi balancesApi;

    private BalancesApiClient(String ratesApiUrl, Builder builder) {
        balancesApi = builder.target(BalancesApi.class, ratesApiUrl);
    }

    public static BalancesApiClient getInstance(String ratesApiUrl, Builder builder) {
        synchronized (BalancesApiClient.class) {
            if (instance == null) {
                instance = new BalancesApiClient(ratesApiUrl, builder);
            }
            return instance;
        }
    }

    public void updateBalance(UpdateBalanceRequest request) {
        balancesApi.updateBalance(API_PATH, request);
    }


}
