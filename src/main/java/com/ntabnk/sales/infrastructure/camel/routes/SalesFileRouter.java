package com.ntabnk.sales.infrastructure.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SalesFileRouter extends RouteBuilder {

    @Value("${file.in.dir}")
    private String fileInput;

    @Override
    public void configure() throws Exception {
        from(String.format("file:%s?move=.done", fileInput))
                .log("Processing: ${exchange.exchangeId}")
                .convertBodyTo(String.class)
                .to("bean:salesFileInputHandler")
                .to("bean:salesFileAggregatorHandler")
                .to("bean:salesFileOutputHandler")
                .log("Finished: ${exchange.exchangeId}");
    }

}