package sparkcontrollers;

import spark.Spark;

import static common.Constants.OK;
import static common.Constants.PLAIN_TEXT;

public class VersionController {

    private static final String VERSION = "1.1";

    public static void start() {
        Spark.get("/version", (req, res) -> {
            res.status(OK);
            res.type(PLAIN_TEXT);
            return VERSION;
        });
    }
}
