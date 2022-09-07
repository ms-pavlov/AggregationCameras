package ru.kilai.config;

import io.netty.handler.codec.http.multipart.HttpData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.NettyOutbound;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import ru.kilai.config.exeptions.BedRequestException;

import java.util.Optional;
import java.util.function.Function;

public class HttpResponseTask implements RequestTask {
    private static final Logger log = LoggerFactory.getLogger(HttpResponseTask.class);
    private final HttpServerRequest request;
    private final HttpServerResponse response;

    public HttpResponseTask(HttpServerRequest request, HttpServerResponse response) {
        Optional.ofNullable(request).orElseThrow(BedRequestException::new);

        this.request = request;
        this.response = response;
    }

    @Override
    public NettyOutbound execute(Function<HttpData, Mono<String>> function) {
        log.debug("request from: {}, uri: {}", request.remoteAddress(), request.uri());

        return response
                .sendString(
                        request
                                .receiveForm()
                                .flatMap(function));
    }
}
