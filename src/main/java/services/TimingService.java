package services;

import configuration.ConfigSingleton;
import sparkcontrollers.ProductController;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class TimingService {

    private static final Logger LOGGER = Logger.getLogger(ConfigSingleton.class.getName());

    public static void startAgent() {
        ConfigSingleton config = ConfigSingleton.getInstance();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                LOGGER.info("TimerTask executing counter is: " + config.getCounter());
                CoffeeReportingService.sendData(ProductController.getProducts());
                config.setCounter(config.getCounter()+1);
            }
        };
        Timer timer = new Timer("MyTimer");
        timer.scheduleAtFixedRate(timerTask, (long)config.getFrequency(), (long)config.getFrequency());
    }
}
