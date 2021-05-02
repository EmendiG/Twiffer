package pl.mat.model.twitter;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class Message {

    private String id;

    @EqualsAndHashCode.Exclude
    private Message retweetedStatus;

    @EqualsAndHashCode.Exclude
    private Date createdAt;

    @EqualsAndHashCode.Exclude
    private String text;

    @EqualsAndHashCode.Exclude
    private User user;

    @EqualsAndHashCode.Exclude
    private String lang;

    @EqualsAndHashCode.Exclude
    private Long quoteCount;

    @EqualsAndHashCode.Exclude
    private Long replyCount;

    @EqualsAndHashCode.Exclude
    private Long retweetCount;

    @EqualsAndHashCode.Exclude
    private Long favoriteCount;

}