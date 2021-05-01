package pl.mat.processor;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.mat.converter.TweetConverter;
import pl.mat.filter.TweetFilter;
import pl.mat.model.db.Tweet;
import pl.mat.model.twitter.Message;
import pl.mat.repository.TweetRepository;

@Slf4j
@Component
@Builder
public class TwitterTweetProcessorImpl implements TwitterTweetProcessor {

    private final static Gson gson = new GsonBuilder()
            .setDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private final TweetRepository tweetRepository;

    public TwitterTweetProcessorImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public void processStringMessage(String tweetStringMsg) {
        Message message = gson.fromJson(tweetStringMsg, Message.class);
        if (message.getRetweetedStatus() != null && TweetFilter.isValid(message.getRetweetedStatus())) {
            Tweet tweet = TweetConverter.toTweet(message.getRetweetedStatus());
            tweetRepository.save(tweet);
        }
    }

}
