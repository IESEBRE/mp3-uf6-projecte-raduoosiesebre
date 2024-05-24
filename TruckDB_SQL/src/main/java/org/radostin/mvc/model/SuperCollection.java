package org.radostin.mvc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class SuperCollection<T> implements Serializable {

    public static enum Type{
        ONE(1), TWO(2);

        private int code;

        public int getCode(){
            return code;
        }

        Type(int code){
            this.code=code;
        }
    }

    private List<T> trucks;
    private TreeSet<T> trucksTreeSet;
    private Type code;

    public Type getCode() {
        return code;
    }

    public void setCode(Type code) {
        if (this.code.getCode() == 1 && this.code.getCode() == 2) {
            if (trucksTreeSet != null) {
                trucksTreeSet.clear();
                trucksTreeSet.addAll(trucks);
            }
        } else if (this.code.getCode() == 2 && this.code.getCode() == 1) {
            trucks.clear();
            if (trucksTreeSet != null) {
                trucks.addAll(trucksTreeSet);
            }
        }
        this.code = code;
    }

    public SuperCollection(Type code) {
        trucks = new ArrayList<>();
        trucksTreeSet = new TreeSet<>();
        this.code = code;
    }

    public void addTruck(T object) {
        if (this.code.getCode() == 1) {
            trucks.add(object);
        } else if (this.code.getCode() == 2) {
            trucksTreeSet.add(object);
        }
    }

    public void removeTruck(T object) {
        if (this.code.getCode() == 1) {
            trucks.remove(object);
        } else if (this.code.getCode() == 2) {
            trucksTreeSet.remove(object);
        }
    }

    public List<T> getAllTrucks() {
        if (this.code.getCode() == 1) {
            return new ArrayList<>(trucks);
        } else {
            return new ArrayList<>(trucksTreeSet);
        }
    }
}
