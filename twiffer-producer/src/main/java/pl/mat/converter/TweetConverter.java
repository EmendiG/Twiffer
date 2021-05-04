package pl.mat.converter;

import pl.mat.model.db.Tweet;
import pl.mat.model.twitter.Message;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TweetConverter {

    public static Tweet fromMessage(Message message) {
        return Tweet.builder()
                .messageId(message.getId())
                .createdAt(LocalDateTime.ofInstant(message.getCreatedAt().toInstant(), ZoneOffset.UTC ) )
                .text(message.getText())
                .quoteCount(message.getQuoteCount())
                .replyCount(message.getReplyCount())
                .retweetCount(message.getRetweetCount())
                .favoriteCount(message.getFavoriteCount())
                .authorId(message.getUser().getId())
                .authorCreatedAt(LocalDateTime.ofInstant(message.getUser().getCreatedAt().toInstant(), ZoneOffset.UTC))
                .authorScreenName(message.getUser().getScreenName())
                .authorFollowersCount(message.getUser().getFollowersCount())
                .authorStatusesCount(message.getUser().getStatusesCount())
                .build();
    }

}
