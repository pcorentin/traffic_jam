package geolocator.gmaps.datamodel;

/**
 * Created by pcorentin on 11/10/2015.
 */
public class Distance {
    private long value;
    private String text;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
