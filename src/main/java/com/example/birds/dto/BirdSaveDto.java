package com.example.birds.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * DTO used for REST API operations.
 */
@Schema(description = "Data Transfer Object for updating or creating a bird.")
public class BirdSaveDto {

    @Schema(description = "Name of the bird", example = "Sparrow")
    private final String name;

    @Schema(description = "Color of the bird", example = "Black")
    private final String color;

    @Schema(description = "Weight of the bird in grams", example = "50.5")
    private final double weight;

    @Schema(description = "Height of the bird in centimeters", example = "15.0")
    private final double height;

    public BirdSaveDto(String name, String color, double weight, double height) {
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BirdSaveDto)) return false;
        BirdSaveDto that = (BirdSaveDto) o;
        return Double.compare(getWeight(), that.getWeight()) == 0 && Double.compare(getHeight(), that.getHeight()) == 0 && Objects.equals(getName(), that.getName()) && Objects.equals(getColor(), that.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getColor(), getWeight(), getHeight());
    }
}
