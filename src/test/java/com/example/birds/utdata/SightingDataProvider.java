package com.example.birds.utdata;

import com.example.birds.model.Sighting;

import java.time.LocalDateTime;


/**
 * Interface used for Unit Testing.
 */
public interface SightingDataProvider {
    /**
     * Builds an object used for testing.
     * @return Sighting
     */
    default Sighting buildSighting() {
        final LocalDateTime localDateTime = LocalDateTime.parse("2023-07-18T10:00:00");
        final Sighting sighting = new Sighting("1", "location", localDateTime);
        sighting.setId("2");
        return sighting;
    }
}
