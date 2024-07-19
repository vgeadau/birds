package com.example.birds.repository;

import com.example.birds.model.Sighting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Sighting repository class.
 */
public interface SightingRepository extends MongoRepository<Sighting, String> {

    /**
     * Finds a list of {@link Sighting} objects based on the provided parameter.
     * @param birdId {@link String}
     * @return List of {@link Sighting} objects
     */
    List<Sighting> findByBirdId(String birdId);

    /**
     * Finds a list of {@link Sighting} objects based on the provided parameter.
     * @param location {@link String}
     * @return List of {@link Sighting} objects
     */
    List<Sighting> findByLocation(String location);

    /**
     * Finds a list of {@link Sighting} objects based on the provided parameters.
     * @param startDateTime {@link LocalDateTime}
     * @param endDateTime {@link LocalDateTime}
     * @return List of {@link Sighting} objects
     */
    List<Sighting> findByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    /**
     * Finds a list of ID(s) having the same {@link com.example.birds.model.Bird} id.
     * </br>
     * First parameter represents the position of the function parameter which is 0.
     * </br>
     * Second parameter represents the position of the ID column which is 1.
     * </br>
     * @param birdId {@link String}
     * @return List of {@link String}
     */
    @Query(value="{ 'birdId' : ?0 }", fields="{ '_id' : 1 }")
    List<String> findSightingIdsByBirdId(String birdId);
}