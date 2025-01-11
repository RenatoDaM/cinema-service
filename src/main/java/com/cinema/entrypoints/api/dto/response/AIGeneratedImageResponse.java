package com.cinema.entrypoints.api.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@Data
public class AIGeneratedImageResponse {
    private List<Data> imageDataList;

    @Getter
    @Setter
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    public static class Data {
        private String imageUrl;
        // for models that return the image in byte format instead of url
        private byte[] image;
        private String description;
    }

    public AIGeneratedImageResponse(List<Data> imageDataList) {
        this.imageDataList = imageDataList;
    }
}
