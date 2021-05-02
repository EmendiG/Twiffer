package pl.mat.config;

import com.twitter.hbc.httpclient.auth.OAuth1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class TwitterConfig {
    @Value("${spring.social.twitter.apiKey}")
    private String apiKey;

    @Value("${spring.social.twitter.apiSecret}")
    private String apiSecret;

    @Value("${spring.social.twitter.accessToken}")
    private String token;

    @Value("${spring.social.twitter.accessTokenSecret}")
    private String tokenSecret;

    @Value("${spring.social.twitter.searchTerm}")
    private String searchTerm;

    @Bean
    public OAuth1 authentication() {
        return new OAuth1(apiKey, apiSecret, token, tokenSecret);
    }

    @Bean
    @Scope(value = "prototype")
    public BlockingQueue<String> blockingQueue() {
        return new LinkedBlockingQueue<>(100);
    }


    @Bean
    public List<String> searchTerms() {
        return List.of(searchTerm);
    }
}
