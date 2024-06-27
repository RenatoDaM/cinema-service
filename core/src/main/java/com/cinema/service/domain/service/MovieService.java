package com.cinema.service.domain.service;

import com.cinema.service.domain.entity.Movie;
import com.cinema.service.domain.mapper.MovieMapper;
import com.cinema.service.domain.repository.MovieRepository;
import com.cinema.service.rest.dto.request.MovieCreateRequest;
import com.cinema.service.rest.dto.response.MovieListResponse;
import com.cinema.service.rest.dto.response.MovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class MovieService {
    public final MovieRepository movieRepository;
    @Value("${cinema.movie-image-upload-directory}")
    private String movieImagesPath;
    private final ImageService imageService;

    public MovieResponse create(MovieCreateRequest movieCreateRequest) {
        var movieEntity = MovieMapper.INSTANCE.fromCreateRequest(movieCreateRequest);
        return MovieMapper.INSTANCE.toDto(movieRepository.save(movieEntity));
    }

    public void saveMovieImage(MultipartFile[] movieImage, Long movieId) throws IOException {
        var movieEntity = movieRepository.findById(movieId)
                .orElseThrow();
        String adsImagesString = "";
        for (MultipartFile imageFile : movieImage) {
            adsImagesString += imageService.saveImage(movieImagesPath, imageFile);
        }
        movieEntity.setImagePath(adsImagesString);
        movieRepository.save(movieEntity);
    }

    public Page<MovieListResponse> findAll(int page, int size) {
        Page<Movie> movies = movieRepository.findAll(PageRequest.of(page, size));
        return movies.map(movie -> MovieMapper.INSTANCE.toDtoList(movie));
    }

    public byte[] getMovieImage(Long movieId) throws IOException {
        String path = findById(movieId).getImagePath();
        Path imagePath = Paths.get(movieImagesPath, path);

        return Files.readAllBytes(imagePath);
    }

    public MovieResponse findById(Long id) {
        Movie movieEntity = movieRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Movie with id: " + id + " not found"));
            return MovieMapper.INSTANCE.toDto(movieEntity);
    }
}
