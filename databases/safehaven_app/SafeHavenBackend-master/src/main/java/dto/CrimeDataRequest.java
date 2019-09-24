package dto;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * POJO class for storing a crime data request.
 * 
 * @author obrien.matt@husky.neu.edu
 */
public class CrimeDataRequest {

    private Integer offenseId;
    private boolean full;
    private boolean shooting;
    private Date datetime;
    private Double latitude;
    private Double longitude;
    private Integer locationId;
    private String offenseCode;
    private String offenseDesc;

    public void setFull(boolean b) {
        this.full = b;
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

    public void setOffenseCode(String offenseCode) {
        this.offenseCode = offenseCode;
    }

    public void setOffenseDesc(String offenseDesc) {
        this.offenseDesc = offenseDesc;
    }

    public boolean isFull() {
        return this.full;
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

    public String getOffenseCode() {
        return this.offenseCode;
    }

    public String getOffenseDesc() {
        return this.offenseDesc;
    }

    public Integer getLocation() {
        return this.locationId;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("@{CrimeOffenseData Request} [ offenseId : ");
        sb.append(offenseId);
        sb.append(", full : ");
        sb.append(full);
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
        sb.append(", offenseCode : ");
        sb.append(offenseCode);
        sb.append(", offenseDesc : ");
        sb.append(offenseDesc);
        sb.append(" ]");

        return sb.toString();
    }

}