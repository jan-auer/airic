package org.spaceapps.aircheck.server.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Location {

    private double latitude;
    private double longitude;
    private long accuracy;

    public Location() {
    }

    @Column(name = "location_lat")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Column(name = "location_long")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "location_accuracy")
    public long getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(long accuracy) {
        this.accuracy = accuracy;
    }

}
