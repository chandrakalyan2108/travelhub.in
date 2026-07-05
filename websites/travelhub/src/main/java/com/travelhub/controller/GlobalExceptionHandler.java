package com.travelhub.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleAny(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage() != null ? ex.getMessage() : "Something went wrong.");
        return "error";
    }
}
