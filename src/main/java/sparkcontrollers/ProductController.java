package sparkcontrollers;

import configuration.ConfigSingleton;
import kong.unirest.json.JSONObject;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;

public class ProductController {

    private static List<String> products = new ArrayList<>();

    public static void start() {
        ConfigSingleton config = ConfigSingleton.getInstance();
        config.loadProperties();
        Spark.post("/product", (req, res) -> {
            products.add((new JSONObject(req.body())).getString("productName"));
            res.status(200);
            res.body("");
            return res;
        });
        Spark.delete("/product", (req, res) -> {
            products.remove((new JSONObject(req.body())).getString("productName"));
            res.status(200);
            res.body("");
            return res;
        });
        Spark.get("/products", (req, res) -> {
            JSONObject js = new JSONObject();
            js.put("products", products);
            return js;
        });
    }
}
