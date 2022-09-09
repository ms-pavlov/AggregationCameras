package ru.kilai;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.http.multipart.HttpData;
import reactor.netty.http.client.HttpClient;
import reactor.tools.agent.ReactorDebugAgent;
import ru.kilai.config.SimplerThreadFactory;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.server.config.ServerConfig;
import ru.kilai.server.routs.PostBindStrategy;
import ru.kilai.server.routs.PostRoutBinder;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.servise.strategies.*;

import java.util.concurrent.Executors;

public class AggregationService {

/*
todo сделать класс для клиента
    Сделать RequestHandler для получения и агрегирования данных
    написать интеграционные и юнит тесты
* */

    public static void main(String... args) {
        System.out.println("In's work...");
        ReactorDebugAgent.init();

        var client = HttpClient.create().runOn(new NioEventLoopGroup(2,
                new SimplerThreadFactory("client-event-")));

        var executorService = Executors.newFixedThreadPool(4,
                new SimplerThreadFactory("worker-"));


        RequestHandler<HttpData, String> getParams = new LogWrapper<>(new SchedulerWrapper<>(
                new GetRequestParameters(),
                executorService))
                .andThen(new LogWrapper<>(new SchedulerWrapper<>(
                        new GetServiceContent(client),
                        executorService)));

        var actionFactory = new CustomServiceActionFactory<HttpData, String>();

        var bindStrategy = new PostBindStrategy(getParams, actionFactory);

        ServerConfig serverConfig = new AggregationServerConfig(8080, 4);
        new AggregationHttpServer(serverConfig)
                .route(new PostRoutBinder("/", bindStrategy).bind())
                .start();
    }
}
