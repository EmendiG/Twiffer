package pl.mat.filter;

import pl.mat.model.twitter.Message;


public class TweetFilter {

    private final static long EIGHT_DAYS_MILLIS = 8 * 86_400 * 1_000;

    public static boolean isValid(Message message) {
        return isSignificantTweet(message) && isPostedRecently(message);
    }

    private static boolean isSignificantTweet(Message message) {
        return message.getQuoteCount() + message.getRetweetCount() + message.getRetweetCount() + message.getFavoriteCount() > 4000;
    }

    private static boolean isPostedRecently(Message message) {
        return message.getCreatedAt().getTime() > System.currentTimeMillis() - EIGHT_DAYS_MILLIS;
    }

}
