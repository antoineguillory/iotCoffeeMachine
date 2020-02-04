package sparkcontrollers;

import common.Constants;
import configuration.ConfigSingleton;
import jsend.JSendResponses;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import spark.Spark;

import static common.Constants.OK;
import static common.Constants.JSON;
import static common.Constants.BAD_REQ;
import static common.Constants.FAILURE;
import static common.Constants.SUCCESS;


import java.util.logging.Logger;

public class ConfigController {

    private static final Logger LOGGER = Logger.getLogger(ConfigSingleton.class.getName());

    public static void start() {
        ConfigSingleton config = ConfigSingleton.getInstance();

        Spark.get("/config", (req, res) -> {
            res.status(OK);
            res.type(JSON);
            JSONObject js = new JSONObject();
            js.put("url", config.getServerURL());
            js.put("port", config.getServerPort());
            return js;
        });

        Spark.post("/config", (req, res) -> {
            JSONObject conf = new JSONObject(req.body());
            try {
                config.setServerPort(conf.getInt("port"));
                config.setServerURL(conf.getString("url"));
                LOGGER.info("New configuration : " + config.getServerURL() + ":" + config.getServerPort());
                res.status(OK);
                res.body(JSendResponses.getBody(conf, SUCCESS));
            } catch(JSONException je) {
                res.status(BAD_REQ);
                res.body(JSendResponses.getBody(conf, FAILURE));
            }
            res.type(JSON);

            return res.body();
        });
    }
}
