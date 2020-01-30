package sparkcontrollers;

import spark.Spark;

public class VersionController {

    private static final String VERSION = "1.1";

    public static void start() {
        Spark.get("/version", (req, res) -> VERSION);
    }
}
