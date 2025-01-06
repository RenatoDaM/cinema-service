package com.cinema.client;

import com.cinema.dto.DALLERequest;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "openai", url = "https://api.openai.com/v1")
@Headers("Content-Type: application/json")
public interface OpenAIClient {
    @PostMapping("/images/generations")
    byte[] generateImage(@RequestHeader("Authorization") String authToken, DALLERequest request);
}
