package com.cinema.core.service;

import com.cinema.core.domain.entity.Movie;
import com.cinema.core.domain.mapper.MovieMapper;
import com.cinema.dataproviders.database.repository.MovieRepository;
import com.cinema.entrypoints.api.dto.request.MovieCreateRequest;
import com.cinema.entrypoints.api.dto.response.MovieListResponse;
import com.cinema.entrypoints.api.dto.response.MovieResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {
    public final MovieRepository movieRepository;

    private final ImageService imageService;

    public final ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(MovieService.class);

    public MovieResponse create(MovieCreateRequest movieCreateRequest) {
        var movieEntity = MovieMapper.INSTANCE.fromCreateRequest(movieCreateRequest);
        return MovieMapper.INSTANCE.toDto(movieRepository.save(movieEntity));
    }

    public void saveMovieImage(MultipartFile[] movieImage, Long movieId) throws IOException {
        var movieEntity = movieRepository.findById(movieId)
                .orElseThrow();

        String imageIdentifier = movieEntity.getImagePath();

        if (movieEntity.getImagePath() == null) {
             imageIdentifier = UUID.randomUUID().toString();
        }

        for (MultipartFile imageFile : movieImage) {
            imageService.saveImage(imageIdentifier, imageFile);
        }
        movieEntity.setImagePath(imageIdentifier);
        movieRepository.save(movieEntity);
    }

    public Page<MovieListResponse> findAll(int page, int size) {
        Page<Movie> movies = movieRepository.findAll(PageRequest.of(page, size));
        return movies.map(movie -> MovieMapper.INSTANCE.toDtoList(movie));
    }

    public byte[] getMovieImage(Long movieId) throws IOException {
        String imageIdentifier = findById(movieId).getImagePath();
        return imageService.getImage(imageIdentifier);
    }

    public MovieResponse findById(Long id) {
        Movie movieEntity = movieRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Movie with id: " + id + " not found"));
            return MovieMapper.INSTANCE.toDto(movieEntity);
    }

    @Transactional
    public void deleteMovie(Long id) throws IOException {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movie with id: " + id + " not found"));

        movieRepository.deleteById(id);
        log.info("Movie with ID {} was deleted: {}", movie.getId(), objectMapper.writeValueAsString(movie));
        imageService.deleteImage(movie.getImagePath());
    }
}
