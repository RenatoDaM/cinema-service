package com.cinema.interfaces;

import com.cinema.dto.AIGeneratedImageResponse;
import com.cinema.dto.AIImagePrompt;

public interface ImageGenerator {
    AIGeneratedImageResponse generateImage(AIImagePrompt imagePrompt);
}
