package com.example.birds.repository;

import com.example.birds.model.Bird;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Bird repository class.
 */
public interface BirdRepository extends MongoRepository<Bird, String> {

    /**
     * Finds a list of {@link Bird} objects by provided parameter.
     * @param name {@link String}
     * @return a List of {@link Bird} objects
     */
    List<Bird> findByName(String name);

    /**
     * Finds a list of {@link Bird} objects by provided parameter.
     * @param color {@link String}
     * @return a List of {@link Bird} objects
     */
    List<Bird> findByColor(String color);
}
