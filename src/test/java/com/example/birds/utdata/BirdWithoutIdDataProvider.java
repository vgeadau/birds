package com.example.birds.utdata;

import com.example.birds.model.Bird;


/**
 * Interface used for Unit Testing.
 */
public interface BirdWithoutIdDataProvider {
    /**
     * Builds an object used for testing.
     * @return Bird
     */
    default Bird buildBirdWithoutId() {
        return new Bird("name", "color", 12.3d, 12.4d);
    }
}
