package dto;

/**
 * POJO class for storing LocationDetails
 * 
 * @author wilson.v@husky.neu.edu
 */
public class LocationDetails {
    private String name;
    private LocationDetailCenter center;

    public LocationDetails(String name, LocationDetailCenter center) {
        this.name = name;
        this.center = center;

    }

    public LocationDetails() {
        this.name = null;
        this.center = null;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCenter(LocationDetailCenter center) {
        this.center = center;
    }

    public String getName() {
        return this.name;
    }

    public LocationDetailCenter getCenter() {
        return this.center;
    }

    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("[ name : ");
        sb.append(this.name);
        sb.append(", center : ");
        sb.append(this.center);
        sb.append("]");

        return sb.toString();
    }
}