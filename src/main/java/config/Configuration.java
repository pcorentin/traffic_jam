package config;

import geolocator.gmaps.GmapLocation;

/**
 * Created by pcorentin on 11/10/2015.
 */
public class Configuration {

    private String gmap_time_key;
    private GmapLocation origin;
    private GmapLocation destination;
    private int interval;
    private String transport;

    public String getGmap_time_key() {
        return gmap_time_key;
    }

    public void setGmap_time_key(String gmap_time_key) {
        this.gmap_time_key = gmap_time_key;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public GmapLocation getOrigin() {
        return origin;
    }

    public void setOrigin(GmapLocation origin) {
        this.origin = origin;
    }

    public GmapLocation getDestination() {
        return destination;
    }

    public void setDestination(GmapLocation destination) {
        this.destination = destination;
    }

    public String getTransport() {

        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }
}
