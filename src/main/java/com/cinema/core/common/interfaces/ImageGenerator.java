package com.cinema.core.common.interfaces;

import com.cinema.entrypoints.api.dto.request.AIImagePrompt;
import com.cinema.entrypoints.api.dto.response.AIGeneratedImageResponse;

public interface ImageGenerator {
    AIGeneratedImageResponse generateImage(AIImagePrompt imagePrompt);
}
