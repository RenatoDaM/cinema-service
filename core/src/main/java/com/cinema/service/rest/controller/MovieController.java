package com.cinema.service.rest.controller;

import com.cinema.service.rest.dto.request.MovieCreateRequest;
import com.cinema.service.rest.dto.response.MovieListResponse;
import com.cinema.service.domain.service.MovieService;
import com.cinema.service.rest.dto.response.MovieResponse;
import org.apache.tika.exception.TikaException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
@CrossOrigin(origins = "${services.bff}")
@Validated
public class MovieController {
    private final MovieService movieService;

    @GetMapping(
            value = "/get-image/{movieId}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getMovieImage(@PathVariable Long movieId) throws IOException {
        return movieService.getMovieImage(movieId);
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(
            @RequestBody @Validated MovieCreateRequest movie
    ) {
        return ResponseEntity.ok().body(movieService.create(movie));
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<Void> saveMovieImage(
            @RequestBody MultipartFile[] movieImage,
            @PathVariable Long movieId
    ) throws IOException, TikaException {
        movieService.saveMovieImage(movieImage, movieId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<MovieListResponse>> getAllMoviePaginated(
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movieService.findAll(page, size));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long movieId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movieService.findById(movieId));
    }
}
