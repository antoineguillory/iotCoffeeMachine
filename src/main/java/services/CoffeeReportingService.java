package services;

import configuration.ConfigSingleton;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.Map;
import java.util.logging.Logger;

class CoffeeReportingService {
    private static final Logger LOGGER = Logger.getLogger(ConfigSingleton.class.getName());

    static void sendData(Map<String, Integer> products) {

        ConfigSingleton config = ConfigSingleton.getInstance();
        JSONObject status = new JSONObject();
        JSONArray productsMap = new JSONArray();

        JSONObject j = new JSONObject();

        for(String prod : products.keySet()) {
            j.put(prod, products.get(prod));
        }
        productsMap.put(j);
        LOGGER.info(j.toString());
        status.put("products", productsMap);
        status.put("port", config.getAgentPort());
        LOGGER.info(status.toString());
        try {
            Unirest.post(config.getServerURL() + ":" + config.getServerPort() + "/data")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(status).asJson();
        } catch(UnirestException exc) {
            LOGGER.warning("Envoi périodique de message au serveur échoué.");
        }

    }
}
