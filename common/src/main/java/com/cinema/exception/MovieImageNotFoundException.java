package com.cinema.exception;

public class MovieImageNotFoundException extends RuntimeException {
    public MovieImageNotFoundException(String msg, Throwable err){
        super(msg, err);
    }
}
