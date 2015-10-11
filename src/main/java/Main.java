import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import config.Configuration;
import geolocator.IGeoDataAccess;
import geolocator.gmaps.GmapDataAccess;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;

/**
 * Created by pcorentin on 11/10/2015.
 */
class Main {

    static public void main(String args[]) {
        // create Options object
        Options options = new Options();

        // add t option
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
        JsonReader readerConf = null;
        try {
            readerConf = new JsonReader(new FileReader(confPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Gson gson = new Gson();
        Configuration configuration = gson.fromJson(readerConf, Configuration.class);

        IGeoDataAccess accessor = new GmapDataAccess(configuration.getGmap_time_key());
        while (true) {
            try {
                Date time = accessor.getTravelTime(configuration.getOrigin(),
                        configuration.getDestination(),
                        configuration.getTransport()
                );
                Thread.sleep(configuration.getInterval() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
