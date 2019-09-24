package dto;
/**
 * POJO class for CrimeRateData
 * 
 * @author obrien.matt@husky.neu.edu
 */
public class CrimeRateDataByZipCode {
    private int zipCode;
    private double count;

    public CrimeRateDataByZipCode(int loc, double a) {
        this.zipCode = loc;
        this.count = a;
    }

    public void setZipCode(int loc) {
        this.zipCode = loc;
    }

    public void setCount(double a) {
        this.count = a;
    }

    public int getZipCode() {
        return this.zipCode;
    }

    public double getCount() {
        return this.count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("@{CrimeRateDataByZipCode request} [ zipCode : ");
        sb.append(zipCode);
        sb.append(", avg : ");
        sb.append(count);
        sb.append(" ]");

        return sb.toString();
    }
}