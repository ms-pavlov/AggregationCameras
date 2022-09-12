package ru.kilai.servise.handlers;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GetParameters implements RequestHandler<Map.Entry<String, List<String>>, String>  {
    @Override
    public Flux<String> apply(Map.Entry<String, List<String>> parameters) {
        return Optional.ofNullable(parameters)
                .filter(parameter -> parameter.getKey().equals("url"))
                .map(Map.Entry::getValue)
                .map(strings -> Flux.fromStream(strings.stream()))
                .orElse(Flux.empty());
    }
}
