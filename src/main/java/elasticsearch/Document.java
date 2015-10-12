package elasticsearch;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentBuilderString;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 * Created by pcorentin on 11/10/2015.
 */
public class Document {


    private static final XContentBuilderString TIMESTAMP = new XContentBuilderString("timestamp");
    private String label;
    private String transport;
    private String date;
    private long time;

    public Document(String label, String transport, long time) {
        this.label = label;
        this.transport = transport;
        this.time = time;
        this.date = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public XContentBuilder getJsonBuilder() {

        try {
            return jsonBuilder().startObject()
                    .field("label", label)
                    .field("transport", transport)
                    .field("commute_time", time)
                    .field("date", date)
                    .endObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
