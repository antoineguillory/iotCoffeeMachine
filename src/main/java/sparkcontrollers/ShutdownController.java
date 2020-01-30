package sparkcontrollers;

import spark.Spark;

public class ShutdownController {
    public static void start() {
        Spark.post("/shutdown", (req, res) -> {
            Spark.stop();
            Spark.awaitStop();
            res.status(200);
            System.exit(1);
            return res;
        });
    }
}
