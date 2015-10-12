package geolocator.gmaps.datamodel;

/**
 * Created by pcorentin on 11/10/2015.
 */
public class Answer {

    public static final String OK = "OK";
    public static final String INVALID_REQUEST = "INVALID_REQUEST";
    public static final String MAX_ELEMENTS_EXCEEDED = "MAX_ELEMENTS_EXCEEDED";
    public static final String OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
    public static final String REQUEST_DENIED = "REQUEST_DENIED";
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";



    private String status;
    private String[] origin_addresses;
    private String[] destination_addresses;
    private Row[] rows;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(String[] origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public String[] getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(String[] destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public Row[] getRows() {
        return rows;
    }

    public void setRows(Row[] rows) {
        this.rows = rows;
    }
}
