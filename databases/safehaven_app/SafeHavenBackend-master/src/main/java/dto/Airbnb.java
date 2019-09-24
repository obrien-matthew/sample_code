package dto;

/**
 * POJO class for storing Airbnb data
 * 
 * @author wilson.v@husky.neu.edu
 */
public class Airbnb extends HousingData {

    private Integer pricePerNight;
    private Integer ratingScore;
    private Integer reviewsPerMonth;
    private Double latitude;
    private Double longitude;
    private Boolean hostIsSuperHost;
    private String roomType;
    private Integer beds;
    private Integer baths;
    private String locationName;

    public Airbnb() {
        this.pricePerNight = null;
        this.ratingScore = null;
        this.reviewsPerMonth = null;
        this.latitude = null;
        this.longitude = null;
        this.hostIsSuperHost = null;
        this.roomType = null;
        this.beds = null;
        this.baths = null;
        this.locationName = null;
    }

    public void setPricePerNight(Integer pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setRatingScore(Integer ratingScore) {
        this.ratingScore = ratingScore;
    }

    public void setReviewsPerMonth(Integer reviewsPerMonth) {
        this.reviewsPerMonth = reviewsPerMonth;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setHostIsSuperHost(Boolean hostIsSuperHost) {
        this.hostIsSuperHost = hostIsSuperHost;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getPricePerNight() {
        return this.pricePerNight;
    }

    public Integer getRatingScore() {
        return this.ratingScore;
    }

    public Integer getReviewsPerMonth() {
        return this.reviewsPerMonth;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Boolean getHostIsSuperHost() {
        return this.hostIsSuperHost;
    }

    public String getRoomType() {
        return this.roomType;
    }

    public Integer getBeds() {
        return this.beds;
    }

    public Integer getBaths() {
        return this.baths;
    }

    public String getLocationName() {
        return this.locationName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("@{Airbnb} [ pricePerNight : ");
        sb.append(pricePerNight);
        sb.append(" , ratingScore : ");
        sb.append(ratingScore);
        sb.append(", reviewsPerMonth : ");
        sb.append(reviewsPerMonth);
        sb.append(", latitude : ");
        sb.append(latitude);
        sb.append(", longitude : ");
        sb.append(longitude);
        sb.append(", hostIsSuperHost : ");
        sb.append(hostIsSuperHost);
        sb.append(", roomType : ");
        sb.append(roomType);
        sb.append(", beds : ");
        sb.append(beds);
        sb.append(", baths : ");
        sb.append(baths);
        sb.append(", locationName : ");
        sb.append(locationName);
        sb.append(" ]");

        return sb.toString();
    }
}