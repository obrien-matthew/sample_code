package dto;

/**
 * POJO class for storing crime counts from Analyze Boston.
 * 
 * @author obrien.matt@husky.neu.edu
 */
public class CrimeStatistics {
    private String crime;
    private Integer count;

    public CrimeStatistics(String c, Integer n) {
        this.crime = c;
        this.count = n;
    }

    public String getCrime() {
        return this.crime;
    }

    public Integer getCount() {
        return this.count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[ Crime-type : ");
        sb.append(this.crime);
        sb.append(", Count : ");
        sb.append(this.count);
        sb.append(" ]");

        return sb.toString();
    }
}