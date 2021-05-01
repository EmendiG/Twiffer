package pl.mat.client;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.mat.processor.TwitterTweetProcessor;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Getter
@Component
@Builder
@AllArgsConstructor
public class TwitterClientImpl implements TwitterClient {

    private final TwitterTweetProcessor twitterMessageProcessor;

    private final Authentication authentication;

    private final BlockingQueue<String> stringLinkedBlockingQueue;

    private final List<String> searchTerms;

    @Override
    public void startFetchProcess() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        final TwitterFetchThread threadFetcher = createFetchThread(executorService);
        executorService.submit(threadFetcher);
        executorService.shutdown();
    }

    private TwitterFetchThread createFetchThread(ExecutorService executorService) {
        final Hosts httpHosts = new HttpHosts(Constants.STREAM_HOST);
        final StatusesFilterEndpoint statusesFilterEndpoint = new StatusesFilterEndpoint();
        statusesFilterEndpoint.trackTerms(searchTerms);
        final BasicClient client = new ClientBuilder()
                .hosts(httpHosts)
                .endpoint(statusesFilterEndpoint)
                .authentication(authentication)
                .processor(new StringDelimitedProcessor(stringLinkedBlockingQueue))
                .build();

        return TwitterFetchThread.builder()
                .executorService(executorService)
                .client(client)
                .stringLinkedBlockingQueue(stringLinkedBlockingQueue)
                .twitterMessageProcessor(twitterMessageProcessor)
                .build();
    }

}
