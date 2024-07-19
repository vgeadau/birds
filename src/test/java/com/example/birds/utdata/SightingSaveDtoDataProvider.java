package com.example.birds.utdata;

import com.example.birds.dto.SightingSaveDto;


/**
 * Interface used for Unit Testing.
 */
public interface SightingSaveDtoDataProvider {

    /**
     * Builds an object used for testing.
     * @return SightingSaveDto
     */
    default SightingSaveDto buildSightingSaveDto() {
        return new SightingSaveDto("2", "location", "2023-07-18T10:00:00");
    }
}
