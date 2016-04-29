package org.spaceapps.aircheck.server.domain;

public enum EventType {

    LOW_CO2("Low CO₂"),
    LOW_CO("Low CO"),
    LOW_TEMPERATURE("Low Temperature"),
    LOW_HUMIDITY("Low Humidity"),

    HIGH_CO2("High CO₂"),
    HIGH_CO("High CO"),
    HIGH_TEMPERATURE("High Temperature"),
    HIGH_HUMIDITY("High Humidity");

    private final String displayName;

    EventType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
