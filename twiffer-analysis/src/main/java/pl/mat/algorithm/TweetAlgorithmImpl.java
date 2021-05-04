package pl.mat.algorithm;

import pl.mat.model.db.Tweet;

import java.time.ZoneOffset;
import java.util.List;

public class TweetAlgorithmImpl implements TweetAlgorithm {

    @Override
    public Tweet calculateMostImpactfulTweet(List<Tweet> consideredTweets) {
        for (Tweet tweet : consideredTweets) {
            long tweetSum = tweet.getQuoteCount() + tweet.getReplyCount() + tweet.getRetweetCount() + tweet.getFavoriteCount();
            long authorCreated = tweet.getAuthorCreatedAt().atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
            Long authorFollowersCount = tweet.getAuthorFollowersCount();
            Long authorStatusesCount = tweet.getAuthorStatusesCount();

        }
        return null;
    }
}
