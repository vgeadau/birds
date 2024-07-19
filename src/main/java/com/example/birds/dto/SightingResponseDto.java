package com.example.birds.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO used for REST API operations.
 */
@Schema(description = "Data Transfer Object for sighting response")
public class SightingResponseDto {

    @Schema(description = "ID of the sighting", example = "60d5ec49f23e4d3b8c6a72f9")
    private final String id;

    @Schema(description = "Bird response",
            example = "{ \"id\": \"60d5ec49f23e4d3b8c6a72f9\" \"name\": \"Sparrow\","
                    + " \"color\": \"Black\", \"weight\": 50.5, \"height\": 15.0 }")
    private final BirdResponseDto birdResponseDto;

    @Schema(description = "Location name", example = "Santa Monica")
    private final String location;

    @Schema(description = "Date and time", example = "2024-07-18T15:30:00")
    private final LocalDateTime dateTime;

    public SightingResponseDto(String id, BirdResponseDto birdResponseDto, String location, LocalDateTime dateTime) {
        this.id = id;
        this.birdResponseDto = birdResponseDto;
        this.location = location;
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public BirdResponseDto getBirdResponseDto() {
        return birdResponseDto;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SightingResponseDto)) return false;
        SightingResponseDto that = (SightingResponseDto) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getBirdResponseDto(), that.getBirdResponseDto()) && Objects.equals(getLocation(), that.getLocation()) && Objects.equals(getDateTime(), that.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBirdResponseDto(), getLocation(), getDateTime());
    }
}
