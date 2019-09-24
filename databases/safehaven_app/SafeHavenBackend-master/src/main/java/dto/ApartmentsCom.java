package dto;

/**
 * POJO class for Storing Information from appartments.com
 * 
 * @author wilson.v@husky.neu.edu
 */
public class ApartmentsCom extends HousingData {

    private String street;
    private Integer rent;
    private Double latitude;
    private Double longitude;
    private Integer beds;
    private Integer baths;
    private Integer sqrft;
    private String leaseInfo;
    private String locationName;
    private Integer postalCode;

    public ApartmentsCom() {
        this.street = null;
        this.rent = null;
        this.latitude = null;
        this.longitude = null;
        this.beds = null;
        this.baths = null;
        this.sqrft = null;
        this.leaseInfo = null;
        this.locationName = null;
        this.postalCode = null;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    public void setSqft(Integer sqft) {
        this.sqrft = sqft;
    }

    public void setLeaseInfo(String leaseInfo) {
        this.leaseInfo = leaseInfo;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return this.street;
    }

    public Integer getRent() {
        return this.rent;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Integer getBeds() {
        return this.beds;
    }

    public Integer getBaths() {
        return this.baths;
    }

    public Integer getSqft() {
        return this.sqrft;
    }

    public String getLeaseInfo() {
        return this.leaseInfo;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public Integer getPostalCode() {
        return this.postalCode;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("@{ApartmentsCom} [ street : ");
        sb.append(street);
        sb.append(", rent : ");
        sb.append(rent);
        sb.append(", latitude : ");
        sb.append(latitude);
        sb.append(", longitude : ");
        sb.append(longitude);
        sb.append(", beds : ");
        sb.append(beds);
        sb.append(", baths : ");
        sb.append(baths);
        sb.append(", sqrft : ");
        sb.append(sqrft);
        sb.append(", leaseInfo : ");
        sb.append(leaseInfo);
        sb.append(", locationName : ");
        sb.append(locationName);
        sb.append(", postalCode : ");
        sb.append(postalCode);
        sb.append(" ]");

        return super.toString();
    }

}