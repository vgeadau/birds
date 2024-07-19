package com.example.birds.utdata;

import com.example.birds.dto.BirdResponseDto;

/**
 * Interface used for Unit Testing.
 */
public interface BirdResponseDtoDataProvider {
    /**
     * Builds an object used for testing.
     * @return BirdResponseDto
     */
    default BirdResponseDto buildBirdResponseDto() {
        return new BirdResponseDto("1", "name", "color", 12.3d, 12.4d);
    }
}
