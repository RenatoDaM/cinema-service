package com.cinema.service;

import com.cinema.client.OpenAIClient;
import com.cinema.dto.DALLERequest;
import com.cinema.interfaces.ImageGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "cinema.image-generator", havingValue = "openai")
public class OpenAIImageGenerator implements ImageGenerator {

    private final String OPEN_AI_TOKEN = "change to get from secrets manager";
    private final OpenAIClient openAIClient;

    @Override
    public byte[] generateImage(String model, String prompt, Integer width, Integer height, Integer imageQuantity) {
        return openAIClient.generateImage(OPEN_AI_TOKEN, new DALLERequest(model, prompt, imageQuantity, width + "x" + height));
    }
}
