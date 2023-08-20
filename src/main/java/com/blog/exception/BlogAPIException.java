package com.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BlogAPIException extends RuntimeException{

    public BlogAPIException(HttpStatus badRequest, String msg){
        super(msg);
    }
}
