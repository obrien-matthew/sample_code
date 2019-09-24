package dto;

/**
 * POJO class for storing crime and offense data from Analyze Boston.
 * 
 * @author obrien.matt@husky.neu.edu
 */
public class CrimeOffenseData extends CrimeData {

    private String offenseCode;
    private String offenseDesc;

    public CrimeOffenseData() {
        super();
        this.offenseCode = null;
        this.offenseDesc = null;
    }

    public void setOffenseCode(String offenseCode) {
        this.offenseCode = offenseCode;
    }

    public void setOffenseDesc(String offenseDesc) {
        this.offenseDesc = offenseDesc;
    }

    public String getOffenseCode() {
        return this.offenseCode;
    }

    public String getOffenseDesc() {
        return this.offenseDesc;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("@{CrimeOffenseData} [ offenseId : ");
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
        sb.append(", offenseCode : ");
        sb.append(offenseCode);
        sb.append(", offenseDesc : ");
        sb.append(offenseDesc);
        sb.append(" ]");

        return sb.toString();
    }

}