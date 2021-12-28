package com.example.musicat.controller;


import lombok.extern.slf4j.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;


@ControllerAdvice
@Slf4j
public class GlobalExceptionResolver {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, Model model) {
        log.error("exception : {}", exception.getMessage());
        model.addAttribute("exception", exception);
        return "error";
    }

}




























