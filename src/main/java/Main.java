import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import config.Configuration;
import elasticsearch.Document;
import geolocator.IGeoDataAccess;
import geolocator.gmaps.GmapDataAccess;
import org.apache.commons.cli.*;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;


/**
 * Created by pcorentin on 11/10/2015.
 */
class Main {

    static public void main(String args[]) {

        Options options = new Options();
        // add config option
        options.addOption("c", true, "java client configuration file");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        String confPath = cmd.getOptionValue("c");
        Configuration configuration = getConfiguration(confPath);


        Client client = getElasticSearchClient(configuration);


        IGeoDataAccess accessor = new GmapDataAccess(configuration.getGmap_time_key());
        while (true) {
            try {
                long time = accessor.getTravelTime(configuration.getOrigin(),
                        configuration.getDestination(),
                        configuration.getTransport()
                );
                if (time < 0) break;

                Document document = new Document(configuration.getLabel(),
                        configuration.getTransport(),
                        time
                );
                indexData(client, document);
                Thread.sleep(configuration.getInterval() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Error accessing API");


    }

    private static IndexResponse indexData(Client client, Document document) {

        Gson gson = new Gson();
        IndexResponse response = client.prepareIndex("trafficjam", "commute")
                .setSource(gson.toJson(document))
                .execute()
                .actionGet();
        System.out.println(response.toString());
        return response;
    }

    private static Configuration getConfiguration(String confPath) {

        JsonReader readerConf = null;
        try {
            readerConf = new JsonReader(new FileReader(confPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Gson gson = new Gson();
        Configuration configuration = gson.fromJson(readerConf, Configuration.class);
        return configuration;
    }


    private static Client getElasticSearchClient(Configuration configuration) {
        Node node =
                nodeBuilder()
                        .clusterName(configuration.getCluster())
                        .settings(ImmutableSettings.settingsBuilder().put("http.enabled", false))
                        .settings(ImmutableSettings.settingsBuilder().put("node.data", false))
                        .settings(ImmutableSettings.settingsBuilder().put("transport.host", configuration.getPublishAddress()))
                        .settings(ImmutableSettings.settingsBuilder().put("discovery.zen.ping.multicast.enabled", false))
                        .settings(ImmutableSettings.settingsBuilder().put("discovery.zen.ping.unicast.hosts", "127.0.0.1:9300"))
                                //           .settings(Settings.settingsBuilder().put("path.home", "/"))
                        .client(true)
                        .node();
        return node.client();

    }

}
