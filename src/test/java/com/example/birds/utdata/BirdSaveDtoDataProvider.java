package com.example.birds.utdata;

import com.example.birds.dto.BirdSaveDto;


/**
 * Interface used for Unit Testing.
 */
public interface BirdSaveDtoDataProvider {
    /**
     * Builds an object used for testing.
     * @return BirdSaveDto
     */
    default BirdSaveDto buildBirdSaveDto() {
        return new BirdSaveDto("name", "color", 12.3d, 12.4d);
    }
}
