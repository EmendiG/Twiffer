package pl.mat.analyser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mat.algorithm.TweetAlgorithm;
import pl.mat.model.db.Tweet;
import pl.mat.repository.TweetRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TweetAnalyserImpl implements TweetAnalyser {

    private final TweetRepository tweetRepository;
    private final TweetAlgorithm tweetAlgorithm;

    @Autowired
    public TweetAnalyserImpl(TweetRepository tweetRepository, TweetAlgorithm tweetAlgorithm) {
        this.tweetRepository = tweetRepository;
        this.tweetAlgorithm = tweetAlgorithm;
    }

    @Override
    public void analyseTweetsForGivenDateTime(LocalDateTime analyzedDateTime) {
        LocalDateTime normalizeDateTime = normalizeDateTime(analyzedDateTime);
        Optional<List<Tweet>> relatedTweets = getRelatedTweets(normalizeDateTime);
        if (relatedTweets.isPresent()) {
            List<Tweet> tweets = relatedTweets.get();
            Tweet tweet = tweetAlgorithm.calculateMostImpactfulTweet(tweets);
        }
    }

    private Optional<List<Tweet>> getRelatedTweets(LocalDateTime normalizedDateTime) {
        LocalDateTime from = normalizedDateTime.minusMinutes(5);
        LocalDateTime to = normalizedDateTime.plusMinutes(5);
        return tweetRepository.findByCreatedAtBetween(from, to);
    }

    private LocalDateTime normalizeDateTime(LocalDateTime analyzedDateTime) {
        return  analyzedDateTime
                .withSecond(0)
                .withNano(0)
                .plusMinutes((65-analyzedDateTime.getMinute())%5);
    }

}
