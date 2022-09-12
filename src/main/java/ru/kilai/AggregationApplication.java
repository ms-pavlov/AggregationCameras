package ru.kilai;

import reactor.tools.agent.ReactorDebugAgent;
import ru.kilai.config.ServiceConfig;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.server.routs.GetBindStrategy;
import ru.kilai.server.routs.GetRoutBinder;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.servise.config.AggregationHandlerConfig;

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

        server.route(new GetRoutBinder("/",
                new GetBindStrategy(
                        handlerConfig.prepGetAggregationHandler()
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
