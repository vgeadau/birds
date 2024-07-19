package com.example.birds.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 'MONGODB Entity' used for persisting Bird objects.
 */
@Document(collection = "birds")
public class Bird {
    @Id
    private String id;
    private String name;
    private String color;
    private double weight;
    private double height;

    public Bird(String name, String color, double weight, double height) {
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}