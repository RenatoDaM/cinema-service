package com.cinema.service;

import com.cinema.client.OpenAIClient;
import com.cinema.dto.AIGeneratedImageResponse;
import com.cinema.dto.AIImagePrompt;
import com.cinema.dto.DALLERequest;
import com.cinema.dto.DALLEResponse;
import com.cinema.interfaces.ImageGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "cinema.image-generator", havingValue = "openai")
public class OpenAIImageGenerator implements ImageGenerator {

    private final String OPEN_AI_TOKEN = "Bearer token";

    private final OpenAIClient openAIClient;

    @Override
    public AIGeneratedImageResponse generateImage(AIImagePrompt imagePrompt) {
        DALLEResponse dalleResponse =
                openAIClient.generateImage(OPEN_AI_TOKEN, new DALLERequest(imagePrompt.getModel(), imagePrompt.getPrompt(), imagePrompt.getN(), imagePrompt.getResolution()));

        List<AIGeneratedImageResponse.Data> imageDataList = dalleResponse.getData().stream()
                .map(data -> {
                    AIGeneratedImageResponse.Data imageData = new AIGeneratedImageResponse.Data();
                    imageData.setImageUrl(data.getUrl());
                    imageData.setDescription(data.getRevisedPrompt());
                    imageData.setImage(null);
                    return imageData;
                })
                .collect(Collectors.toList());

        return new AIGeneratedImageResponse(imageDataList);
    }
}
