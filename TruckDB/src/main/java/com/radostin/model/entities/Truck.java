package com.radostin.model.entities;

import com.radostin.model.type.EngineType;

/**
 * Truck POJO entity class.
 * @author Radostin V. Ivanov.
 * @version 1.0
 */

public class Truck {
    private int id;
    private String brand;
    private Double horsepower;
    private Integer kilometers;
    private Boolean trailer;
    private EngineType engineType;

    /**
     * Constructor for the Truck POJO entity.
     * @param id The ID of the truck is automatic, no input needed.
     * @param brand Insert the brand of the truck.
     * @param horsepower Insert the horsepower of the truck.
     * @param kilometers Insert the kilometers of the truck.
     * @param trailer Check the checkbox if the truck has a trailer.
     * @param engineType Choose the engine type of the truck.
     */

    public Truck(int id, String brand, Double horsepower, Integer kilometers, Boolean trailer, EngineType engineType) {
        this.id = id;
        this.brand = brand;
        this.horsepower = horsepower;
        this.kilometers = kilometers;
        this.trailer = trailer;
        this.engineType = engineType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Double horsepower) {
        this.horsepower = horsepower;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public Boolean getTruckHasTrailerCheckBox() {
        return trailer;
    }

    public void setTruckHasTrailerCheckBox(Boolean trailer) {
        this.trailer = trailer;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }
}
