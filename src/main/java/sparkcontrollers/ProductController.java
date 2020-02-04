package sparkcontrollers;

import configuration.ConfigSingleton;
import jsend.JSendResponses;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import spark.Request;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static common.Constants.*;

public class ProductController {

    private static Map<String, Integer> products = new HashMap<>();

    public static Map<String, Integer> getProducts() {
        return products;
    }

    private static String getProductName(Request req){
        try {
            return (new JSONObject(req.body())).getString("productName");
        } catch(JSONException jse) {
            return "";
        }
    }

    private static Integer getHowMany(Request req) {
        try {
            return (new JSONObject(req.body())).getInt("howMany");
        } catch (JSONException jse) {
            return 1;
        }
    }

    public static void start() {
        ConfigSingleton config = ConfigSingleton.getInstance();
        config.loadProperties();
        Spark.post("/product", (req, res) -> {

            if(getProductName(req).equals("")){
                res.type(JSON);
                res.status(BAD_REQ);
                res.body(JSendResponses.getBody(new JSONObject(req.body()), FAILURE));
                return res.body();
            }

            String prod = getProductName(req);
            Integer howMany = getHowMany(req);

            Integer prodNb = 0;
            if(products.containsKey(prod)) {
                prodNb = products.get(prod);
            }
            if(prodNb==0){
                products.put(prod, howMany);
                res.status(CREATED);
            } else {
                products.replace(prod, prodNb+howMany);
                res.status(OK);
            }
            res.type(JSON);
            res.body(JSendResponses.getBody(new JSONObject(req.body()), SUCCESS));
            return res.body();
        });

        Spark.delete("/product", (req, res) -> {
            String prod = getProductName(req);
            Integer howMany = getHowMany(req);
            if(products.containsKey(prod)) {
                Integer prodNb = products.get(prod);
                if(0>=prodNb-howMany){
                    prodNb=0;
                }
                if(prodNb==0){
                    products.remove(prod);
                } else {
                    products.replace(prod, prodNb-howMany);
                }
                res.status(OK);
                res.type(JSON);
                res.body(JSendResponses.getBody(new JSONObject(req.body()), SUCCESS));
            } else {
                res.status(NOT_FOUND);
                res.type(JSON);
                res.body(JSendResponses.getBody(new JSONObject(req.body()), FAILURE));
            }
            return res.body();
        });

        Spark.get("/products", (req, res) -> {
            JSONObject js = new JSONObject();
            js.put("products", products);
            res.status(OK);
            res.type(JSON);
            return js;
        });
    }
}
