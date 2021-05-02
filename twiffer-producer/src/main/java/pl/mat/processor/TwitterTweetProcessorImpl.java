package pl.mat.processor;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.mat.converter.TweetConverter;
import pl.mat.filter.TweetFilter;
import pl.mat.model.db.Tweet;
import pl.mat.model.twitter.Message;
import pl.mat.repository.TweetRepository;
import pl.mat.service.KafkaTweetProducer;

@Slf4j
@Component
@Builder
public class TwitterTweetProcessorImpl implements TwitterTweetProcessor {

    private final static Gson gson = new GsonBuilder()
            .setDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private final TweetRepository tweetRepository;
    private final KafkaTweetProducer kafkaTweetProducer;
    private final String kafkaTopic;

    public TwitterTweetProcessorImpl(
            TweetRepository tweetRepository,
            KafkaTweetProducer kafkaTweetProducer,
            @Value(value = "${spring.kafka.template.default-topic}") String kafkaTopic) {
        this.tweetRepository = tweetRepository;
        this.kafkaTweetProducer = kafkaTweetProducer;
        this.kafkaTopic = kafkaTopic;
    }

    @Override
    public void processStringMessage(String tweetStringMsg) {
        Message message = gson.fromJson(tweetStringMsg, Message.class);
        // getRetweetedStatus==[message that initial message replies to]
        // to get the data that this message replies to (already has some likes)
        if (message.getRetweetedStatus() != null && TweetFilter.isValid(message.getRetweetedStatus())) {
            Tweet tweet = TweetConverter.fromMessage(message.getRetweetedStatus());
            tweetRepository.save(tweet);
            System.out.println("XXXXXXXXXD =======> " + tweet.toString());
            kafkaTweetProducer.send(kafkaTopic, tweet.getAuthorScreenName());
        }
    }

}
