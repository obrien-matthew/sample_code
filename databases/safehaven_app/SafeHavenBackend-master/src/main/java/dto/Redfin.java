package dto;

/**
 * POJO class for Storing Information from redfin.com
 * 
 * @author wilson.v@husky.neu.edu
 */
public class Redfin extends HousingData {

    private String street;
    private Integer price;
    private Double latitude;
    private Double longitude;
    private Integer squarefeet;
    private Integer beds;
    private Integer baths;
    private String locationName;
    private Integer ppsqft;
    private Integer lotSize;
    private String comments;
    private String isNewConstruction;
    private Integer yearBuilt;
    private Integer postalCode;

    public Redfin() {
        this.street = null;
        this.price = null;
        this.latitude = null;
        this.longitude = null;
        this.beds = null;
        this.baths = null;
        this.squarefeet = null;
        this.locationName = null;
        this.postalCode = null;

        this.ppsqft = null;
        this.lotSize = null;
        this.comments = null;
        this.isNewConstruction = null;
        this.yearBuilt = null;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public void setSquarefeet(Integer squarefeet) {
        this.squarefeet = squarefeet;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public void setPpsqft(Integer ppsqft) {
        this.ppsqft = ppsqft;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setIsNewConstruction(String isNewConstruction) {
        this.isNewConstruction = isNewConstruction;
    }

    public void setYearBuilt(Integer yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public String getStreet() {
        return this.street;
    }

    public Integer getPrice() {
        return this.price;
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

    public Integer getSquarefeet() {
        return this.squarefeet;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public Integer getPostalCode() {
        return this.postalCode;
    }

    public Integer getPpsqft() {
        return this.ppsqft;
    }

    public Integer getLotSize() {
        return this.lotSize;
    }

    public String getComments() {
        return this.comments;
    }

    public String getIsNewConstruction() {
        return this.isNewConstruction;
    }

    public Integer getYearBuilt() {
        return this.yearBuilt;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("@{Redfin} [ street : ");
        sb.append(street);
        sb.append(", price : ");
        sb.append(price);
        sb.append(", latitude : ");
        sb.append(latitude);
        sb.append(", longitude : ");
        sb.append(longitude);
        sb.append(", beds : ");
        sb.append(beds);
        sb.append(", baths : ");
        sb.append(baths);
        sb.append(", squarefeet : ");
        sb.append(squarefeet);
        sb.append(", locationName : ");
        sb.append(locationName);
        sb.append(", postalCode : ");
        sb.append(postalCode);
        sb.append(", ppsqft : ");
        sb.append(ppsqft);
        sb.append(", lotSize : ");
        sb.append(lotSize);
        sb.append(", comments : ");
        sb.append(comments);
        sb.append(", isNewConstruction : ");
        sb.append(isNewConstruction);
        sb.append(", yearBuilt : ");
        sb.append(yearBuilt);
        sb.append(" ]");

        return sb.toString();
    }

}