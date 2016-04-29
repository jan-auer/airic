package org.spaceapps.aircheck.server.domain;

import javax.persistence.Embeddable;

@Embeddable
public class SampleData {

    private double co2;
    private double co;
    private double temperature;
    private double humidity;

    public SampleData() {
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

}
