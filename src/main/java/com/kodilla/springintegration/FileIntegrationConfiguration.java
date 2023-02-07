package com.kodilla.springintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

import java.io.File;

@Configuration
public class FileIntegrationConfiguration {

    @Bean
    IntegrationFlow fileIntegrationlow(FileReadingMessageSource fileAdapter, FileTransformer fileTransformer, FileWritingMessageHandler outputFileHandler){
       return IntegrationFlow.from(fileAdapter, config -> config.poller(Pollers.fixedDelay(1000)))
               .transform(fileTransformer, "transformFile")
               .handle(outputFileHandler)
               .get();
    }

    @Bean
    FileReadingMessageSource fileAdapter(){
        FileReadingMessageSource fileReadingMessageSource = new FileReadingMessageSource();
        fileReadingMessageSource.setDirectory(new File("data/input"));
        return fileReadingMessageSource;
    }

    @Bean
    FileTransformer transformer(){
        return new FileTransformer();
    }

    @Bean
    FileWritingMessageHandler outputFileAdapter(){
        File directory = new File("data/output");
        FileWritingMessageHandler handler = new FileWritingMessageHandler(directory);
        handler.setExpectReply(false);

        return handler;
    }
}
