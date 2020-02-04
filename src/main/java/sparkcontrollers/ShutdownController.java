package sparkcontrollers;

import spark.Spark;

import static common.Constants.PLAIN_TEXT;
import static common.Constants.OK;

public class ShutdownController {
    public static void start() {
        Spark.post("/shutdown", (req, res) -> {
            Spark.stop();
            Spark.awaitStop();
            res.status(OK);
            res.type(PLAIN_TEXT);
            System.exit(1);
            return "machine a café arrêtée";
        });
    }
}
