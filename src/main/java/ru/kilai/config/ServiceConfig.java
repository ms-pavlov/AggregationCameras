package ru.kilai.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ServiceConfig {

    private static final List<String> PATHS = List.of("./src/main/resources/config.ini", "./config.ini");

    private String host = "localhost";
    private int port = 8080;
    private int serverEventPoolSize = 2;
    private int clientEventPoolSize = 2;
    private int handlerPoolSize = 2;
    private int retryDelay = 0;

    public ServiceConfig() {
        PATHS.stream()
                .map(File::new)
                .filter(File::exists)
                .findAny().ifPresent(this::init);
    }

    public ServiceConfig(String path) {
        Optional.ofNullable(path)
                .map(File::new)
                .filter(File::exists)
                .ifPresent(this::init);
    }

    private void init(File file) {
        try (var input = new FileInputStream(file)) {
            Properties props = new Properties();
            props.load(input);

            host = props.getProperty("HOST", "localhost");
            port = Integer.parseInt(props.getProperty("PORT", "8080"));
            serverEventPoolSize = Integer.parseInt(props.getProperty("SERVER_EVENT_LOOP_POL_SIZE", "2"));
            clientEventPoolSize = Integer.parseInt(props.getProperty("CLIENT_EVENT_LOOP_POL_SIZE", "2"));
            handlerPoolSize = Integer.parseInt(props.getProperty("HANDLER_POOL_SIZE", "2"));
            retryDelay = Integer.parseInt(props.getProperty("RETRY_DELAY", "0"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getServerEventPoolSize() {
        return serverEventPoolSize;
    }

    public int getClientEventPoolSize() {
        return clientEventPoolSize;
    }

    public int getHandlerPoolSize() {
        return handlerPoolSize;
    }

    public int getRetryDelay() {
        return retryDelay;
    }

}
