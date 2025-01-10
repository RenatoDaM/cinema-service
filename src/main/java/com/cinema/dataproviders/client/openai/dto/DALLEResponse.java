package com.cinema.dataproviders.client.openai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DALLEResponse {
    private long created;
    private List<Data> data;

    @Getter
    @Setter
    public static class Data {
        private String url;

        @JsonProperty("b64_json")
        private String b64Json;

        @JsonProperty("revised_prompt")
        private String revisedPrompt;
    }
}