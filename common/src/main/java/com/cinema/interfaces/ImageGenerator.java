package com.cinema.interfaces;

public interface ImageGenerator {
    byte[] generateImage(String model, String prompt, Integer width, Integer height, Integer imageQuantity);
}
