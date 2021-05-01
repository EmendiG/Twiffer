package pl.mat.client;

import com.twitter.hbc.httpclient.BasicClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.mat.processor.TwitterTweetProcessor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Builder
@AllArgsConstructor
public class TwitterFetchThread extends Thread {

    private final String invalidMessageStart = "{\"limit\":{\"track\"";

    private final TwitterTweetProcessor twitterMessageProcessor;
    private final ExecutorService executorService;
    private final BasicClient client;
    private final BlockingQueue<String> stringLinkedBlockingQueue;

    @Override
    public void run() {
        try {
            client.connect();
            while (true) {
                if (client.isDone()) {
                    log.error("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
                    break;
                }
                String msg = stringLinkedBlockingQueue.poll(1, TimeUnit.SECONDS);
                processMessage(msg);
            }
            client.stop();
        } catch (InterruptedException e) {
            log.error("An exception has occurred!", e);
        } finally {
            executorService.shutdownNow();
        }
    }

    private void processMessage(String msg) {
        if (msg == null) {
            log.warn("Did not receive a message in 1 seconds");
        } else {
           processIncomingTextMessage(msg);
        }
    }

    private void processIncomingTextMessage(String msg) {
        if (!msg.startsWith(invalidMessageStart)) {
            twitterMessageProcessor.processStringMessage(msg);
            log.trace("Received 1 message!");
            log.trace(msg);
        } else {
            log.warn("Received 1 limit track message!");
            log.warn(msg);
        }
    }
}
