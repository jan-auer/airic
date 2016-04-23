package org.spaceapps.aircheck.server.dto;

import java.util.Date;

public class SampleDto {

    private String deviceId;
    private LocationDto location;
    private Date date;
    private SampleDataDto data;

    public SampleDto() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SampleDataDto getData() {
        return data;
    }

    public void setData(SampleDataDto data) {
        this.data = data;
    }
}
