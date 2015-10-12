package geolocator;

import org.jetbrains.annotations.NotNull;

/**
 * Created by pcorentin on 11/10/2015.
 */
public interface IGeoDataAccess {

    String PUBLICTRANSPORT = "transit";
    String DRIVING = "driving";
    String WALKING = "walking";

    @NotNull
    long getTravelTime(IGeoLocation origin, IGeoLocation destination, String transport);

}
