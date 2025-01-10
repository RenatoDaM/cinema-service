package com.cinema.entrypoints.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieListResponse {
    private Long id;
    private String name;
    private String ageGroup;
    private String category;
    private String duration;
    private String imagePath;
}
