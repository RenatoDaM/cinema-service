package com.cinema.service.domain.service;

import com.cinema.service.domain.entity.Movie;
import com.cinema.service.domain.mapper.MovieMapper;
import com.cinema.service.domain.repository.MovieRepository;
import com.cinema.service.rest.dto.request.MovieCreateRequest;
import com.cinema.service.rest.dto.response.MovieListResponse;
import com.cinema.service.rest.dto.response.MovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {
    public final MovieRepository movieRepository;

    private final ImageService imageService;

    public MovieResponse create(MovieCreateRequest movieCreateRequest) {
        var movieEntity = MovieMapper.INSTANCE.fromCreateRequest(movieCreateRequest);
        return MovieMapper.INSTANCE.toDto(movieRepository.save(movieEntity));
    }

    public void saveMovieImage(MultipartFile[] movieImage, Long movieId) throws IOException {
        var movieEntity = movieRepository.findById(movieId)
                .orElseThrow();
        String imageIdentifier = UUID.randomUUID().toString();
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
}
