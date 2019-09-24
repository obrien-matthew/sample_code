package dto;

/**
 * POJO class for storing request information
 * 
 * @author wilson.v@husky.neu.edu
 */
public class HousingListRequest {

    private Integer postalCode;
    private String type;
    private Integer beds;
    private Integer baths;
    private Double priceRange;
    private String startDate;
    private String endDate;

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    public void setPriceRange(Double priceRange) {
        this.priceRange = priceRange;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPostalCode() {
        return this.postalCode;
    }

    public String getType() {
        return this.type;
    }

    public Integer getBeds() {
        return this.beds;
    }

    public Integer getBaths() {
        return this.baths;
    }

    public Double getPriceRange() {
        return this.priceRange;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("@{House List Request} [ postalCode : ");
        sb.append(postalCode);
        sb.append(", type : ");
        sb.append(type);
        sb.append(", beds : ");
        sb.append(beds);
        sb.append(", baths : ");
        sb.append(baths);
        sb.append(", priceRange : ");
        sb.append(priceRange);
        sb.append(", startDate : ");
        sb.append(startDate);
        sb.append(", endDate : ");
        sb.append(endDate);
        sb.append(" ]");

        return sb.toString();
    }
}
