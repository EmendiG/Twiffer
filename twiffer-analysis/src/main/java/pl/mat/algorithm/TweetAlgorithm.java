package pl.mat.algorithm;

import pl.mat.model.db.Tweet;

import java.util.List;

public interface TweetAlgorithm {
    Tweet calculateMostImpactfulTweet(List<Tweet> consideredTweets);
}
