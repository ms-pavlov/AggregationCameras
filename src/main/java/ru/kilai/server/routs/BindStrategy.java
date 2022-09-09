package ru.kilai.server.routs;

import org.reactivestreams.Publisher;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

import java.util.function.BiFunction;

public interface BindStrategy extends BiFunction<HttpServerRequest, HttpServerResponse, Publisher<Void>> {
}
