package ru.kilai.client;

import io.netty.channel.nio.NioEventLoopGroup;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import ru.kilai.config.SimplerThreadFactory;

public class CustomContentHttpClient implements ContentHttpClient {
    private static final int MIN_EVENT_GROUP_SIZE = 1;

    private final HttpClient client;

    public CustomContentHttpClient(int eventGroupSize) {
        this.client = HttpClient.create()
                .runOn(new NioEventLoopGroup(Math.max(MIN_EVENT_GROUP_SIZE, eventGroupSize),
                        new SimplerThreadFactory("client-event-")));
    }

    public CustomContentHttpClient() {
        this(MIN_EVENT_GROUP_SIZE);
    }

    @Override
    public ByteBufFlux get(String uri) {
        return client.get()
                .uri(uri)
                .responseContent();
    }
}
