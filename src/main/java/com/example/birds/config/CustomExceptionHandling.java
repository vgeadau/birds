package com.example.birds.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Custom exception handling, class for handling specific exceptions.
 */
@ControllerAdvice
@RestController
public class CustomExceptionHandling implements HandlerExceptionResolver {

    /**
     * Method that handles exceptions thrown in this application.
     * </br>
     * in case of {@link IllegalArgumentException} response will be set as BAD_REQUEST.
     * </br>
     * in case of {@link IllegalStateException} response will be set as NOT_FOUND.
     * @param ex {@link Exception}
     * @return a {@link ResponseEntity} of {@link String}
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof IllegalStateException) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>("Status: " + status + " - " + ex.getMessage(), status);
    }

    /**
     * resolveException method.
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler {@link Object}
     * @param ex {@link Exception}
     * @return an instance of {@link ModelAndView} object
     */
    @Override
    public ModelAndView resolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {
        // Handle exceptions and customize responses here if needed.
        return new ModelAndView();
    }
}