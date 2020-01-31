package main;

import configuration.ConfigSingleton;
import services.TimingService;
import spark.Spark;
import sparkcontrollers.ConfigController;
import sparkcontrollers.ProductController;
import sparkcontrollers.ShutdownController;
import sparkcontrollers.VersionController;

public class Main {
    public static void main (String[] args){
        loadConfig();
        startServices();
    }

    private static void loadConfig(){
        ConfigSingleton config = ConfigSingleton.getInstance();
        config.loadProperties();
    }

    private static void startServices() {
        ConfigController.start();
        ProductController.start();
        ShutdownController.start();
        VersionController.start();

        TimingService.startAgent();
    }
}
