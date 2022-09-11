package ru.kilai.server.config;

import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.netty.http.server.HttpServer;
import ru.kilai.config.SimplerThreadFactory;

public class AggregationServerConfig implements ServerConfig {
    private static final Logger log = LoggerFactory.getLogger(AggregationServerConfig.class);
    private final static int DEFAULT_PORT = 8080;
    private final static String DEFAULT_HOST = "localhost";
    private final static int MIN_EVENT_POOL_SIZE = 1;

    private final String host;
    private final int port;
    private final int eventPoolSize;

    public AggregationServerConfig(String host, int port, int eventPoolSize) {

        this.host = host;
        this.port = port;
        this.eventPoolSize = eventPoolSize;
    }

    public AggregationServerConfig() {
        this(DEFAULT_HOST, DEFAULT_PORT, MIN_EVENT_POOL_SIZE);
    }

    public AggregationServerConfig(int port) {
        this(DEFAULT_HOST, port, MIN_EVENT_POOL_SIZE);
    }

    public AggregationServerConfig(int port, int eventPoolSize) {
        this(DEFAULT_HOST, port, eventPoolSize);
    }

    @Override
    public HttpServer configure() {
        log.debug("event pool size {}", eventPoolSize);
        return HttpServer
                .create()
                .host(host)
                .port(port)
                .runOn(new NioEventLoopGroup(Math.max(MIN_EVENT_POOL_SIZE, eventPoolSize),
                        new SimplerThreadFactory("server-event-")));
    }
}
