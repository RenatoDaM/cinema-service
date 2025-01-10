package com.cinema.client;

import com.cinema.dto.DALLERequest;
import com.cinema.dto.DALLEResponse;
import feign.Headers;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "openai", url = "https://api.openai.com/v1")
@Headers("Content-Type: application/json")
@Validated
public interface OpenAIClient {
    @PostMapping("/images/generations")
    DALLEResponse generateImage(@RequestHeader("Authorization") String authToken,  @Valid DALLERequest request);
}
