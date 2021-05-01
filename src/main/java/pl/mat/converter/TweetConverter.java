package pl.mat.converter;

import pl.mat.model.db.Tweet;
import pl.mat.model.twitter.Message;

public class TweetConverter {

    public static Tweet toTweet(Message message) {
        return Tweet.builder()
                .messageId(message.getId())
                .createdAt(message.getCreatedAt().getTime())
                .text(message.getText())
                .quoteCount(message.getQuoteCount())
                .replyCount(message.getReplyCount())
                .retweetCount(message.getRetweetCount())
                .favoriteCount(message.getFavoriteCount())
                .authorId(message.getUser().getId())
                .authorCreatedAt(message.getUser().getCreatedAt().getTime())
                .authorScreenName(message.getUser().getScreenName())
                .authorFollowersCount(message.getUser().getFollowersCount())
                .authorStatusesCount(message.getUser().getStatusesCount())
                .build();
    }

}
