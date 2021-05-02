package com.bilyoner.assignment.couponapi.client;

import com.bilyoner.assignment.couponapi.client.model.UpdateBalanceRequest;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface BalancesApi {

    @Headers({
            "Content-Type: application/json"
    })
    @RequestLine("PUT {path}")
    void updateBalance(@Param("path") String path, UpdateBalanceRequest request);

}
