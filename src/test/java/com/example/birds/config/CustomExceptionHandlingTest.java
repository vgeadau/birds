package com.example.birds.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CustomExceptionHandling}.
 */
@ExtendWith(MockitoExtension.class)
public class CustomExceptionHandlingTest {

    private static final String ERROR_MESSAGE = "Some error message";

    private final  CustomExceptionHandling customExceptionHandling = new CustomExceptionHandling();

    @Test
    public void handleException_IllegalArgumentException_should_succeed() {
        // given
        final Exception exception = new IllegalArgumentException(ERROR_MESSAGE);

        // when
        final ResponseEntity<String> result = customExceptionHandling.handleException(exception);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void handleException_IllegalStateException_should_succeed() {
        // given
        final Exception exception = new IllegalStateException(ERROR_MESSAGE);

        // when
        final ResponseEntity<String> result = customExceptionHandling.handleException(exception);

        // then
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }


    @Test
    public void handleException_Other_should_succeed() {
        // given
        final Exception exception = new IOException(ERROR_MESSAGE);

        // when
        final ResponseEntity<String> result = customExceptionHandling.handleException(exception);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void resolveException_should_succeed() {
        // given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final Object handler = new Object();
        final Exception ex = new Exception();

        // when
        final ModelAndView result = customExceptionHandling.resolveException(request, response, handler, ex);

        // then
        assertNotNull(result);
    }
}
