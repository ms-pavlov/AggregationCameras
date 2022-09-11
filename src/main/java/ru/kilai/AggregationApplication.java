package ru.kilai;

import reactor.tools.agent.ReactorDebugAgent;
import ru.kilai.config.ServiceConfig;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.server.routs.GetBindStrategy;
import ru.kilai.server.routs.GetRoutBinder;
import ru.kilai.server.routs.PostBindStrategy;
import ru.kilai.server.routs.PostRoutBinder;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.servise.config.AggregationHandlerConfig;
import ru.kilai.servise.handlers.GetParameters;
import ru.kilai.servise.handlers.PostParameters;

public class AggregationApplication {

    public static void main(String... args) {

        ReactorDebugAgent.init();
        var config = new ServiceConfig();

        var server = new AggregationHttpServer(
                new AggregationServerConfig(
                        config.getHost(),
                        config.getPort(),
                        config.getServerEventPoolSize()));

        var handlerConfig = new AggregationHandlerConfig(
                config.getHandlerPoolSize(),
                config.getClientEventPoolSize(),
                config.getRetryDelay());

        server.route(new PostRoutBinder("/",
                new PostBindStrategy(
                        new PostParameters().andThen(handlerConfig.getRetryHandler()),
                        new CustomServiceActionFactory<>()))
                .bind());
        server.route(new GetRoutBinder("/",
                new GetBindStrategy(new GetParameters()
                        .andThen(handlerConfig.getRetryHandler()),
                        new CustomServiceActionFactory<>()))
                .bind());

        server.start();

        if (args.length > 0) {
            if ("test".equals(args[0])) {
                server.stop();
            }
        }
    }
}
