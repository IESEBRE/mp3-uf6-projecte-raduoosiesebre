package com.radostin.model.type;

public enum EngineType {
    DIESEL,
    PETROL,
    HYBRID,
    ELECTRIC;

    @Override
    public String toString() {
        switch (this) {
            case DIESEL:
                return "DIESEL";
            case PETROL:
                return "PETROL";
            case HYBRID:
                return "HYBRID";
            case ELECTRIC:
                return "ELECTRIC";
            default:
                return super.toString();
        }
    }
}
