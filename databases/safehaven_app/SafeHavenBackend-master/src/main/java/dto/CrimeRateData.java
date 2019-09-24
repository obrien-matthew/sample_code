package dto;

import java.util.List;

/**
 * POJO class for storing crime rate data from Analyze Boston.
 * 
 * @author obrien.matt@husky.neu.edu
 */
public class CrimeRateData {
    private Double avg;
    private List<CrimeRateDataByZipCode> data;

    public CrimeRateData() {
        this.data = null;
    }

    public CrimeRateData(List<CrimeRateDataByZipCode> data) {
        this.data = data;
        this.setAvg();
    }

    public void setAvg() {
        int sum = 0;
        int ctr = 0;
        for (CrimeRateDataByZipCode c : data) {
            sum += c.getCount();
            ctr++;
        }
        this.avg = (double) sum / ctr;
    }

    public Double getAvg() {
        this.setAvg();
        return this.avg;
    }

    public List<CrimeRateDataByZipCode> getData() {
        return this.data;
    }

    public Double getLocData(int zipCode) {
        for (CrimeRateDataByZipCode c : data) {
            if (c.getZipCode() == zipCode) {
                return c.getCount();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("@{CrimeRateData request} [ avg : ");
        sb.append(avg);
        sb.append(", data : [ ");
        for (CrimeRateDataByZipCode c : data) {
            sb.append(c.getZipCode());
            sb.append(" : ");
            sb.append(c.getCount());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" ] ]");

        return sb.toString();
    }
}