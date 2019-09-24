package dto;

/**
 * Default messages probably error message to the FE
 * 
 * @author wilson.v@husky.neu.edu
 */
public class DefaultMessage {
    String message;
    String status;

    public DefaultMessage(String status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("status : ");
        sb.append(status);
        sb.append(", message : ");
        sb.append(message);
        sb.append("]");

        return sb.toString();
    }

}