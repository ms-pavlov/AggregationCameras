package ru.kilai;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.server.config.ServerConfig;
import ru.kilai.server.routs.ActionPostRoutBuilder;
import ru.kilai.servise.actions.GetRequestParameters;
import ru.kilai.servise.actions.GetServiceContent;

import java.io.IOException;
import java.util.function.Function;

public class AggregationService {

/*
todo сделать класс для клиента
    Сделать класс для задания что будет получать HttpData и с помощю класс для клиента обращаться за списком url
    написать интеграционные и юнит тесты
* */

    public static void main(String... args) {
        System.out.println("In's work...");

        ServerConfig serverConfig = new AggregationServerConfig();

        Function<HttpData, Flux<String>> getParams = new GetRequestParameters();
        Function<Flux<String>, Flux<String>> parameters = new GetServiceContent(HttpClient.create());

        new AggregationHttpServer(serverConfig)
                .route(new ActionPostRoutBuilder("/", getParams.andThen(parameters)).build())
                .start();
    }

    private static Flux<String> toFlux(HttpData httpData) {
        try {
            return Flux.just(new String(httpData.get()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
