package geolocator.gmaps.datamodel;

/**
 * Created by pcorentin on 11/10/2015.
 */
public class Element {

    public static final String ELEMENT_OK = "OK";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String ZERO_RESULTS = "ZERO_RESULTS";



    private String status;
    private Duration duration;
    private Distance distance;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
