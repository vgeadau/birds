package com.example.birds.utdata;

import com.example.birds.model.Bird;

/**
 * Interface used for Unit Testing, in order to avoid code duplication for
 * same objects required to be built on different unit tests.
 * A single class for all Unit Test Data could also work, but that breaks SOLID principle,
 * as we don't need to have objects available for those unit tests that don't use those available objects.
 * Using Interfaces with single methods is key solution to have only those needed methods in the unit tests.
 */
public interface BirdDataProvider {
    /**
     * Builds an object used for testing.
     * @return Bird
     */
    default Bird buildBird() {
        final Bird result = new Bird("name", "color", 12.3d, 12.4d);
        result.setId("1");
        return result;
    }
}
