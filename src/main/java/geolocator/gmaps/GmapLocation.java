package geolocator.gmaps;

import geolocator.IGeoLocation;
import org.jetbrains.annotations.NotNull;


/**
 * Created by pcorentin on 11/10/2015.
 */
public class GmapLocation implements IGeoLocation {

    private long latitude;
    private long longitude;
    private String address;

    protected GmapLocation(long latitude, long longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected GmapLocation(String address) {
        this.address = address;
    }

    @NotNull
    public String getParameter() {
        StringBuilder query = new StringBuilder();
        if (this.address.isEmpty()) {
            query.append(this.latitude).append("|").append(this.longitude);

        } else {
            query.append(address);
        }
        return query.toString();
    }

}
