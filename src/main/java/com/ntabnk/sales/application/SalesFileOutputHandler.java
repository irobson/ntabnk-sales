package com.ntabnk.sales.application;

import com.ntabnk.sales.application.exception.ApplicationException;
import com.ntabnk.sales.domain.SalesFileResult;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.component.file.GenericFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class SalesFileOutputHandler {

    @Value("${file.out.dir}")
    private String fileOutput;

    @Handler
    public void handle(Exchange exchange) {
        SalesFileResult body = (SalesFileResult) exchange.getIn().getBody();
        GenericFile file = (GenericFile) exchange.getProperties().get("CamelFileExchangeFile");
        String fileName = file.getFileName();
        generateResultFile(body, fileName);
    }

    private void generateResultFile(SalesFileResult salesFileResult, String fileName) {
        final String resultFile = fileOutput + File.separator + fileName;
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(resultFile))) {
            writer.write(salesFileResult.toString());
        } catch (IOException e) {
            throw new ApplicationException(String.format("Cannot create output file for: ", fileName), e);
        }
    }

}
