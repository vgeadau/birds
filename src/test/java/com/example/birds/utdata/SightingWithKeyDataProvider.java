package com.example.birds.utdata;

import com.example.birds.model.Sighting;

import java.time.LocalDateTime;


/**
 * Interface used for Unit Testing.
 */
public interface SightingWithKeyDataProvider {

    /**
     * Builds an object used for Unit testing.
     * @param key int
     * @return Sighting
     */
    default Sighting buildSighting(int key) {
        final LocalDateTime localDateTime = LocalDateTime.parse("2023-07-18T10:00:00");
        final Sighting result = new Sighting("" + key, "location" + key, localDateTime);
        final int sightId = key + 10;
        result.setId("" + sightId);
        return result;
    }
}
