package dto;

/**
 * POJO class to maintain longitude and latitude information for Neighbourhood
 * center
 * 
 * @author wilson.v@husky.neu.edu
 */
public class LocationDetailCenter {
    private Double latitude;
    private Double longitude;

    public LocationDetailCenter(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("[ longitude : ");
        sb.append(this.longitude);
        sb.append("latitude : ");
        sb.append(this.latitude);
        sb.append(" ]");

        return sb.toString();
    }
}