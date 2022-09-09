package ru.kilai;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;
import reactor.tools.agent.ReactorDebugAgent;
import ru.kilai.config.SimplerThreadFactory;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.server.config.ServerConfig;
import ru.kilai.server.routs.ActionPostRoutBuilder;
import ru.kilai.servise.actions.GetRequestParameters;
import ru.kilai.servise.actions.GetServiceContent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class AggregationService {

/*
todo сделать класс для клиента
    Сделать класс для задания что будет получать HttpData и с помощю класс для клиента обращаться за списком url
    написать интеграционные и юнит тесты
* */

    public static void main(String... args) {
        System.out.println("In's work...");
        ReactorDebugAgent.init();

        var client = HttpClient.create();

        var executorService = Executors.newFixedThreadPool(4,
                new SimplerThreadFactory("worker-"));

        Function<HttpData, Flux<String>> getParams = new GetRequestParameters()
                .andThen(new GetServiceContent(client));

        ServerConfig serverConfig = new AggregationServerConfig();
        new AggregationHttpServer(serverConfig)
                .route(new ActionPostRoutBuilder("/", getParams).build())
                .start();
    }
}
