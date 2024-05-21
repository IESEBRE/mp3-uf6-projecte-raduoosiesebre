package com.radostin.model.entities;

import java.util.Collection;
import java.util.TreeSet;

public class Truck {

    private Long id;
    private String truckBrand;
    private double truckHorsepower;
    private int truckKM;
    private boolean truckIsElectric;

    private Collection<Driver> drivers;


    public Truck(){}

    public Truck(String nom, double truckHorsepower, int truckKM, boolean truckIsElectric, Collection<Driver> drivers) {
        this.truckBrand = nom;
        this.truckHorsepower = truckHorsepower;
        this.truckKM = truckKM;
        this.truckIsElectric = truckIsElectric;
        this.drivers = drivers;
    }

    public Truck(Long id, String nom) {
        this.id = id;
        this.truckBrand = nom;
    }

    public Truck(long id, String nom, double truckHorsepower, int truckKM) {
        this.id = id;
        this.truckBrand = nom;
        this.truckHorsepower = truckHorsepower;
        this.truckKM = truckKM;
    }

    public Truck(long id, String nom, double truckHorsepower, int truckKM, TreeSet<Driver> drivers) {
        this.id = id;
        this.truckBrand = nom;
        this.truckHorsepower = truckHorsepower;
        this.truckKM = truckKM;
        this.drivers = drivers;
    }

    public Collection<Driver> getDrivers() {
        return drivers;
    }

    private void setDrivers(Collection<Driver> drivers) {
        this.drivers = drivers;
    }

    public String getTruckBrand() {
        return truckBrand;
    }

    public void setTruckBrand(String truckBrand) {
        this.truckBrand = truckBrand;
    }

    public double getTruckHorsepower() {
        return truckHorsepower;
    }

    public void setTruckHorsepower(double truckHorsepower) {
        this.truckHorsepower = truckHorsepower;
    }

    public int getTruckKM(){
        return truckKM;
    }

    public void setTruckKM(int truckKM){
        this.truckKM = truckKM;
    }

    public boolean isTruckIsElectric() {
        return truckIsElectric;
    }

    public void setTruckIsElectric(boolean truckIsElectric) {
        this.truckIsElectric = truckIsElectric;
    }

    public static class Driver implements Comparable<Driver>{

        @Override
        public int compareTo(Driver o) {
            return this.truckENUM.compareTo(o.getTruckEnum());
        }

        public static enum TruckENUM {
            TR1("Mercedes-Benz"), TR2("Scania"), TR3("Volvo"),
            TR4("DAF"), TR5("Renault"), TR6("IVECO");
            private String truckBrand;

            private TruckENUM(String truckBrand) {
                this.truckBrand = truckBrand;
            }

            public String getTruckBrand() {
                return truckBrand;
            }

            @Override
            public String toString() {
                return this.name()+" - " + truckBrand;
            }
        }

        private TruckENUM truckENUM;
        private String licenseNumber;

        public Driver(TruckENUM truckENUM, String licenseNumber) {
            this.truckENUM = truckENUM;
            this.licenseNumber = licenseNumber;
        }

        public TruckENUM getTruckEnum() {
            return truckENUM;
        }

        public void setModul(TruckENUM truckENUM) {
            this.truckENUM = truckENUM;
        }

        public String getLicenseNumber() {
            return licenseNumber;
        }

        public void setLicenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
        }
    }
}
