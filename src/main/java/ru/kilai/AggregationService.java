package ru.kilai;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Mono;
import ru.kilai.config.HttpRNettyApplication;
import ru.kilai.config.HttpResponseTask;

import java.io.IOException;

public class AggregationService {

/*
todo вынисти из  HttpRNettyApplication создание сервера в класс CustomRNettyServer impl CustomHttpServer
    сделать класс для клиента
    сделать класс который будет выполнять задания
    Сделать класс для задания что будет получать HttpData и с помощю класс для клиента обращаться за списком url
    написать интеграционные и юнит тесты
* */

    public static void main(String... args) {
        System.out.println("In's work...");

        new HttpRNettyApplication()
                .route(routes -> routes.post("/",
                        (httpServerRequest, httpServerResponse) ->
                                new HttpResponseTask(httpServerRequest, httpServerResponse).execute(AggregationService::getValue)))
                .run();
    }

    private static Mono<String> getValue(HttpData httpData) {
        try {
            return Mono.just(new String(httpData.get()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
