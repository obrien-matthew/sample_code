package dto;

/**
 * POJO class to contain GlobalStatistics data
 * 
 * @author wilson.v@husky.neu.edu
 */
public class HousingStatistics {
    private Integer zipCode;
    private String locationName;
    private Double avgRentPerMonth;
    private Double avgPriceToBuy;
    private Double avgAirbnbPerNight;

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setAvgRentPerMonth(Double avgRentPerMonth) {
        this.avgRentPerMonth = avgRentPerMonth;
    }

    public void setAvgPriceToBuy(Double avgPriceToBuy) {
        this.avgPriceToBuy = avgPriceToBuy;
    }

    public void setAvgAirbnbPerNight(Double avgAirbnbPerNight) {
        this.avgAirbnbPerNight = avgAirbnbPerNight;
    }

    public Integer getZipCode() {
        return this.zipCode;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public Double getAvgRentPerMonth() {
        return this.avgRentPerMonth;
    }

    public Double getAvgPriceToBuy() {
        return this.avgPriceToBuy;
    }

    public Double getAvgAirbnbPerNight() {
        return this.avgAirbnbPerNight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("@{HousingStatistics} [ zipCode : ");
        sb.append(zipCode);
        sb.append(", avgRentPerMonth : ");
        sb.append(avgRentPerMonth);
        sb.append(", avgPriceToBuy : ");
        sb.append(avgPriceToBuy);
        sb.append(", avgAirbnbPerNight : ");
        sb.append(avgAirbnbPerNight);
        sb.append(" ]");

        return sb.toString();
    }
}