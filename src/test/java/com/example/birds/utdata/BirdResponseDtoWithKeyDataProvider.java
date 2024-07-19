package com.example.birds.utdata;

import com.example.birds.dto.BirdResponseDto;


/**
 * Interface used for Unit Testing.
 */
public interface BirdResponseDtoWithKeyDataProvider {

    /**
     * Builds an object used for unit testing.
     * @param key int
     * @return BirdResponseDto
     */
    default BirdResponseDto buildBirdResponseDto(int key) {
        return new BirdResponseDto("" + key, "name" + key, "color" + key, 13.2d, 14.2d);
    }
}
