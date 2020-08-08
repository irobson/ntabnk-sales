package com.ntabnk.sales.infrastructure.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SalesFileRouter extends RouteBuilder {

    public static final String LINE_BREAKER = "\n";

    @Override
    public void configure() throws Exception {
        from("file:/home/robson/Development/test?move=.done")
                .split(body().tokenize(LINE_BREAKER))
                .to("bean:salesFileInputHandler")
                .to("bean:salesFileAggregatorHandler")
                .to("bean:salesFileOutputHandler");
    }

}