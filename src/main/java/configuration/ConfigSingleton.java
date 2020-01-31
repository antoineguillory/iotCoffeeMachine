package configuration;

import spark.Spark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigSingleton {
    // Constantes
    private static final Logger LOGGER = Logger.getLogger(ConfigSingleton.class.getName());
    private static final String FILE_NOT_FOUND_MSG = "Le fichier de configuration n'a pas été trouvé, arrêt immédiat du service";
    private static final String IO_ISSUE_UNKNOWN_MSG = "Problème de lecture du fichier de configuration.";
    private static final String CONFIG_FILE_NAME = "server.cfg";
    private static final String DFLT_AGENTPORT_WARN = "la propriété agentPort est inexistante dans le fichier de configuration. le port 8080 va lui être appliquée.";
    private static final String DFLT_SERVERURL_WARN = "le propriété serverURL est inexistante dans le fichier de configuration. localhost va être choisi par défaut.";
    private static final String DFLT_SERVERPORT_WARN = "la propriété serverPort est inexistante dans le fichier de configuration. le port 80 va lui être appliquée.";
    private static final String DFLT_FREQUENCY_WARN = "la propriété de frequence est inexistante dans le fichier de configuration. la valeur 3000 va lui être appliquée par défaut";

    private static final int FATAL = 1;

    // Gestion du singleton
    private static ConfigSingleton instance = new ConfigSingleton();
    public static ConfigSingleton getInstance() {
        return instance;
    }

    // Données membre
    private boolean hasTriedToLoadPropertyFile = false;
    private int counter = 0;
    private int frequency = 3000;
    private String serverURL = "127.0.0.1";
    private int serverPort = 80;
    private int agentPort = 8080;

    private void checkIfPropertyFileLoaded() {
        if(!hasTriedToLoadPropertyFile) {
            throw new SecurityException();
        }
    }

    public void setServerURL(String serverURL) {
        checkIfPropertyFileLoaded();
        this.serverURL = serverURL;
    }

    public void setServerPort(int serverPort) {
        checkIfPropertyFileLoaded();
        this.serverPort = serverPort;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }


    public int getFrequency() {
        checkIfPropertyFileLoaded();
        return frequency;
    }

    public String getServerURL() {
        checkIfPropertyFileLoaded();
        return serverURL;
    }

    public int getServerPort() {
        checkIfPropertyFileLoaded();
        return serverPort;
    }

    public int getAgentPort() {
        checkIfPropertyFileLoaded();
        return agentPort;
    }

    public int getCounter() {
        checkIfPropertyFileLoaded();
        return counter;
    }

    public void loadProperties() {
        if(!hasTriedToLoadPropertyFile){
            loadPropertiesOnSystem(loadPropertiesFile());
            Spark.port(serverPort);
        }
    }

    private Properties loadPropertiesFile() {
        Properties prop = new Properties();
        try {
            InputStream input = new FileInputStream(CONFIG_FILE_NAME);
            prop.load(input);
        } catch (FileNotFoundException e) {
            LOGGER.severe(FILE_NOT_FOUND_MSG);
            System.exit(FATAL);
        } catch (IOException e) {
            LOGGER.severe(IO_ISSUE_UNKNOWN_MSG);
            System.exit(FATAL);
        }
        return prop;
    }

    private void loadPropertiesOnSystem(Properties prop) {

        this.hasTriedToLoadPropertyFile = true;

        String tmpAgentPort = prop.getProperty("agentPort");
        String tmpServerURL = prop.getProperty("serverURL");
        String tmpServerPort = prop.getProperty("serverPort");
        String tmpFrequency = prop.getProperty("frequency");

        if(null==tmpAgentPort) {
            LOGGER.warning(DFLT_AGENTPORT_WARN);
        } else {
            agentPort = Integer.parseInt(tmpAgentPort);
        }

        if (null==tmpServerURL) {
            LOGGER.warning(DFLT_SERVERURL_WARN);
        } else {
            serverURL = tmpServerURL;
        }

        if (null==tmpServerPort) {
            LOGGER.warning(DFLT_SERVERPORT_WARN);
        } else {
            serverPort = Integer.parseInt(tmpServerPort);
        }

        if(null==tmpFrequency) {
            LOGGER.warning(DFLT_FREQUENCY_WARN);
        } else {
            frequency = Integer.parseInt(tmpFrequency);
        }
    }


}
