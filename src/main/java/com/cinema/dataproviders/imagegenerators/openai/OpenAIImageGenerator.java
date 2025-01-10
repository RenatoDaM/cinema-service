package com.cinema.dataproviders.imagegenerators.openai;

import com.cinema.core.common.interfaces.ImageGenerator;
import com.cinema.dataproviders.client.openai.OpenAIClient;
import com.cinema.dataproviders.client.openai.dto.DALLERequest;
import com.cinema.dataproviders.client.openai.dto.DALLEResponse;
import com.cinema.entrypoints.api.dto.request.AIImagePrompt;
import com.cinema.entrypoints.api.dto.response.AIGeneratedImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "cinema.image-generator", havingValue = "openai")
public class OpenAIImageGenerator implements ImageGenerator {

    private final String OPEN_AI_TOKEN = "Bearer ";

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
