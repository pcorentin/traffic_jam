package geolocator.gmaps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import geolocator.IGeoDataAccess;
import geolocator.IGeoLocation;
import geolocator.gmaps.datamodel.Answer;
import geolocator.gmaps.datamodel.Element;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static geolocator.gmaps.datamodel.Answer.OK;

/**
 * Created by pcorentin on 11/10/2015.
 */
public class GmapDataAccess implements IGeoDataAccess {

    private static final String APIURI = "https://maps.googleapis.com/maps/api/distancematrix/json?";
    private final String key;
    @NotNull
    private final URLCodec urlCodec;

    public GmapDataAccess(String gmap_time_key) {
        this.key = gmap_time_key;
        urlCodec = new URLCodec();
    }


    @NotNull
    public long getTravelTime(@NotNull IGeoLocation origin, @NotNull IGeoLocation destination, @NotNull String transport) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(buildQuery(origin, destination, transport));
        CloseableHttpResponse response = null;
        Answer answer = null;
        try {
            response = httpclient.execute(httpGet);
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();

            InputStream in = entity.getContent();
            JsonReader reader = new JsonReader(new InputStreamReader(in));
            Gson gson = new Gson();
            answer = gson.fromJson(reader, Answer.class);
            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(builder.toJson(answer));
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (answer == null) return -1;
        switch (answer.getStatus()) {
            case OK:
                Element element = answer.getRows()[0].getElements()[0];
                switch (element.getStatus()) {
                    case Element.ELEMENT_OK:
                        return element.getDuration().getValue();
                    default:
                        return -1;
                }
            default:
                return -1;
        }

    }


    private String buildQuery(@NotNull IGeoLocation origin, @NotNull IGeoLocation destination, @NotNull String transport) {

        StringBuilder query = new StringBuilder();
        query.append(APIURI);
        try {
            query.append("origins=").append(urlCodec.encode(origin.getParameter()));
            query.append("&");
            query.append("destinations=").append(urlCodec.encode(destination.getParameter()));
            query.append("&");
            String sTransport = "driving";
            switch (transport) {
                case IGeoDataAccess.PUBLICTRANSPORT:
                    sTransport = "transit";
                    break;
                case IGeoDataAccess.WALKING:
                    sTransport = "walking";
                    break;
            }
            query.append("mode=").append(sTransport);
            query.append("&");
            query.append("key=").append(key);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        System.out.println(APIURI + query.toString());
        return query.toString();

    }
}
