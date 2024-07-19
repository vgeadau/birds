package com.example.birds.utdata;

import com.example.birds.dto.SightingResponseDto;
import com.example.birds.utdata.BirdResponseDtoDataProvider;

import java.time.LocalDateTime;


/**
 * Interface used for Unit Testing.
 */
public interface SightingResponseDtoDataProvider extends BirdResponseDtoDataProvider {
    /**
     * Builds an object used for testing.
     * @return SightingResponseDto
     */
    default SightingResponseDto buildSightingResponseDto() {
        final LocalDateTime dateTime = LocalDateTime.parse("2023-07-18T10:00:00");
        return new SightingResponseDto("2", buildBirdResponseDto(), "location", dateTime);
    }
}
