package dto;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * POJO class for storing crime data from Analyze Boston.
 * 
 * @author obrien.matt@husky.neu.edu
 */
public class CrimeData {

    protected Integer offenseId;
    protected boolean shooting;
    protected Date datetime;
    protected Double latitude;
    protected Double longitude;
    protected Integer locationId;

    public CrimeData() {
        this.offenseId = null;
        this.shooting = false;
        this.datetime = null;
        this.latitude = null;
        this.longitude = null;
        this.locationId = null;
    }

    public void setOffenseId(Integer offense_id) {
        this.offenseId = offense_id;
    }

    public void setShooting(Integer s) {
        if (s == 1) {
            this.shooting = true;
        } else {
            this.shooting = false;
        }
    }

    public void setDatetime(String datetime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.datetime = format.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLocation(Integer loc) {
        this.locationId = loc;
    }

    public Integer getOffenseId() {
        return this.offenseId;
    }

    public boolean getShooting() {
        return this.shooting;
    }

    public Date getDatetime() {
        return this.datetime;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Integer getLocation() {
        return this.locationId;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("@{CrimeData} [ offenseId : ");
        sb.append(offenseId);
        sb.append(", shooting : ");
        sb.append(shooting);
        sb.append(", datetime : ");
        sb.append(datetime);
        sb.append(", latitude : ");
        sb.append(latitude);
        sb.append(", longitude : ");
        sb.append(longitude);
        sb.append(", locationId : ");
        sb.append(locationId);
        sb.append(" ]");

        return sb.toString();
    }

}