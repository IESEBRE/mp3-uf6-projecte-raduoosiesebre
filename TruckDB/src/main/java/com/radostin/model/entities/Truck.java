package com.radostin.model.entities;

import com.radostin.model.type.EngineType;

public class Truck {
    private int id;
    private String brand;
    private Double horsepower;
    private Integer kilometers;
    private Boolean trailer;
    private EngineType engineType;

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
