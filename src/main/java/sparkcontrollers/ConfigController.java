package sparkcontrollers;

import configuration.ConfigSingleton;
import kong.unirest.json.JSONObject;
import spark.Spark;

import java.util.logging.Logger;

public class ConfigController {

    private static final Logger LOGGER = Logger.getLogger(ConfigSingleton.class.getName());
    private static final int OK = 200;

    public static void start() {

        ConfigSingleton config = ConfigSingleton.getInstance();


        Spark.get("/config", (req, res) -> {
            res.status(OK);
            return config.getServerURL() + ":" + config.getServerPort();
        });

        Spark.post("/config", (req, res) -> {
            JSONObject conf = new JSONObject(req.body());
            config.setServerPort(conf.optInt("port", 8080));
            config.setServerURL(conf.optString("url", "127.0.0.1"));
            LOGGER.info("New configuration : " + config.getServerURL() + ":" + config.getServerPort());
            res.status(OK);
            return res;
        });
    }
}
