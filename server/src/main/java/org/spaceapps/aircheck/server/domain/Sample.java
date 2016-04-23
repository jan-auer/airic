package org.spaceapps.aircheck.server.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sample")
public class Sample {

    private long id;
    private String deviceId;
    private Location location;
    private Date date;
    private SampleData data;

    public Sample() {
    }

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "sample_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Embedded
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Embedded
    public SampleData getData() {
        return data;
    }

    public void setData(SampleData data) {
        this.data = data;
    }

}
