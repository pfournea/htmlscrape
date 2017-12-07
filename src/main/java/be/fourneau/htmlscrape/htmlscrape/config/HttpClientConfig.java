package be.fourneau.htmlscrape.htmlscrape.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient getClient() {
        return HttpClientBuilder.create()
                .build();
    }


}
