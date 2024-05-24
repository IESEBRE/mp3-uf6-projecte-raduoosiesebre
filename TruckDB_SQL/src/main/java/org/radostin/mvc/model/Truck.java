package org.radostin.mvc.model;

import java.io.Serializable;
import java.util.Collection;

public class Truck implements Serializable {
    //Static properties of the class
    private static double indexFragment=3.4;

    //Properties of the class (not static)
    private String truckBrand;
    private double truckHorsepower;
    private int truckKM;
    private boolean isHybrid;

    private Collection<Driver> drivers;

    /**
     * Method to insert data for the trucks
     *
     * @param truckBrand      We insert the brand of the truck
     * @param truckHorsepower We insert the horsepower of the truck
     * @param truckKM         We insert the kilometers that has the truck
     * @param isHybrid        We insert true or false for the checkbox if the truck is hybrid or not.
     */
    public Truck(String truckBrand, double truckHorsepower, int truckKM, boolean isHybrid){
        this.truckBrand = truckBrand;
        this.truckHorsepower = truckHorsepower;
        this.truckKM = truckKM;
        this.isHybrid = isHybrid;
    }

    public String toString(){
        return "Truck{" +
                "TruckBrand='" + truckBrand + '\'' +
                "TruckHorsepower='" + truckHorsepower + '\'' +
                "TruckKM='" + truckKM + '\'' +
                "IsHybrid='" + isHybrid +
                (isHybrid ?"hybrid":"notHybrid");
    }

    public Collection<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Collection<Driver> drivers){
        this.drivers = drivers;
    }

    public String getTruckBrand(){
        return truckBrand;
    }

    private void setTruckBrand(){
        this.truckBrand = truckBrand;
    }

    public double getTruckHorsepower(){
        return truckHorsepower;
    }

    private void setTruckHorsepower(){
        this.truckHorsepower = truckHorsepower;
    }

    public int getTruckKM(){
        return truckKM;
    }

    private void setTruckKM(){
        this.truckKM = truckKM;
    }

    public boolean getIsHybrid(){
        return isHybrid;
    }

    public void setHybrid(){
        this.isHybrid = isHybrid;
    }


    public class Driver implements Comparable<Driver>{

        @Override
        public int compareTo(Driver driver){
            return this.brandEnum.compareTo(driver.getBrandEnum());
        }

        public static enum brandEnum {
            TRUCK_BRAND1("Scania"), TRUCK_BRAND2("Mercedes"), TRUCK_BRAND3("Volvo"),
            TRUCK_BRAND4("DAF"), TRUCK_BRAND5("Renault"), TRUCK_BRAND6("Iveco");
            private String truckBrand;

            private brandEnum(String truckBrand){
                this.truckBrand = truckBrand;
            }

            public String getTruckBrand(){
                return truckBrand;
            }

            @Override
            public String toString(){
                return this.name() + " - " + truckBrand;
            }
        }

        private Driver.brandEnum brandEnum;


        public Driver(Driver.brandEnum brandEnum){
            this.brandEnum = brandEnum;
        }

        public Driver.brandEnum getBrandEnum(){
            return brandEnum;
        }

        public void setBrandEnum(Driver.brandEnum brandEnum){
            this.brandEnum = brandEnum;
        }
    }

}