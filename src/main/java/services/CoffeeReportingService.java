package services;

import configuration.ConfigSingleton;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.List;
import java.util.logging.Logger;

public class CoffeeReportingService {
    private static final Logger LOGGER = Logger.getLogger(ConfigSingleton.class.getName());

    private static void sendData(List<String> products) {

        ConfigSingleton config = ConfigSingleton.getInstance();
        JSONObject status = new JSONObject();
        JSONArray productsMap = new JSONArray();

        JSONObject j = new JSONObject();

        for(String prod : products) {
            j.put(prod, 1); // TODO GESTION DES STOCKS
        }
        status.put("products", productsMap);
        status.put("port", config.getAgentPort());
        LOGGER.info(status.toString());
        Unirest.post(config.getServerURL() + ":" + config.getServerPort() + "/data")
            .header("accept", "application/json")
            .header("Content-Type", "application/json")
            .body(status).asJson();
    }
}
