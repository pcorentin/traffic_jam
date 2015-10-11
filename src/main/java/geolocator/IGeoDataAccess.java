package geolocator;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Created by pcorentin on 11/10/2015.
 */
public interface IGeoDataAccess {

    String PUBLICTRANSPORT = "transit";
    String DRIVING = "driving";
    String WALKING = "walking";

    @NotNull
    Date getTravelTime(IGeoLocation origin, IGeoLocation destination, String transport);

}
