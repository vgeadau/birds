package com.example.birds.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * DTO used for REST API operations.
 */
@Schema(description = "Data Transfer Object for sighting crate or update.")
public class SightingSaveDto {

    @Schema(description = "ID of the bird", example = "60d5ec49f23e4d3b8c6a72f8")
    private final String birdId;
    @Schema(description = "Location name", example = "Santa Monica")
    private final String location;
    @Schema(description = "Date and time", example = "2024-07-18T15:30:00")
    private final String dateTime;

    public SightingSaveDto(String birdId, String location, String dateTime) {
        this.birdId = birdId;
        this.location = location;
        this.dateTime = dateTime;
    }

    public String getBirdId() {
        return birdId;
    }

    public String getLocation() {
        return location;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SightingSaveDto)) return false;
        SightingSaveDto that = (SightingSaveDto) o;
        return Objects.equals(getBirdId(), that.getBirdId()) && Objects.equals(getLocation(), that.getLocation()) && Objects.equals(getDateTime(), that.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBirdId(), getLocation(), getDateTime());
    }
}
