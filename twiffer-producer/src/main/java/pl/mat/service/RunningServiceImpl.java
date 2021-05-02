package pl.mat.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.mat.client.StockClient;
import pl.mat.client.TwitterClient;

@Service
public class RunningServiceImpl implements RunningService {

    private final StockClient stockClient;
    private final TwitterClient twitterClient;

    public RunningServiceImpl(TwitterClient twitterClient, StockClient stockClient) {
        this.stockClient = stockClient;
        this.twitterClient = twitterClient;
    }

    @Scheduled(cron = "${spring.social.twitter.cron}")
    public void schedule() {
        stockClient.startFetchProcess();
    }

    @Override
    public void startProcess() throws InterruptedException {
        twitterClient.startFetchProcess();
    }
}
