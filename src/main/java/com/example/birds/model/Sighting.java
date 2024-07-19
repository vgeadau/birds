package com.example.birds.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 'MONGODB Entity' used for persisting Sighting objects.
 */
@Document(collection = "sightings")
public class Sighting {
    @Id
    private String id;

    private String birdId;
    private String location;
    private LocalDateTime dateTime;

    public Sighting(String birdId, String location, LocalDateTime dateTime) {
        this.birdId = birdId;
        this.location = location;
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirdId() {
        return birdId;
    }

    @SuppressWarnings("unused")
    public void setBirdId(String birdId) {
        this.birdId = birdId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @SuppressWarnings("unused")
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}