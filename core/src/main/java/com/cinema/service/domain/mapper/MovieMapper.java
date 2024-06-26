package com.cinema.service.domain.mapper;

import com.cinema.service.domain.entity.Movie;
import com.cinema.service.rest.dto.request.MovieCreateRequest;
import com.cinema.service.rest.dto.response.MovieListResponse;
import com.cinema.service.rest.dto.response.MovieResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "synopsis", source = "synopsis")
    @Mapping(target = "ageGroup", source = "ageGroup")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "releaseDate", source = "releaseDate")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "director", source = "director")
    @Mapping(target = "movieCast", source = "movieCast")
    @Mapping(target = "producer", source = "producer")
    MovieResponse toDto(Movie entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "ageGroup", source = "ageGroup")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "duration", source = "duration")
    MovieListResponse toDtoList(Movie entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "synopsis", source = "synopsis")
    @Mapping(target = "ageGroup", source = "ageGroup")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "releaseDate", source = "releaseDate")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "director", source = "director")
    @Mapping(target = "movieCast", source = "movieCast")
    @Mapping(target = "producer", source = "producer")
    Movie toEntity(MovieResponse dto);

    Movie fromCreateRequest(MovieCreateRequest dto);
}
