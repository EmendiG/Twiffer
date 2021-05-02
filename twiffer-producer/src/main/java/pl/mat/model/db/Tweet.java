package pl.mat.model.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@ToString
@Builder
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="tweets")
public class Tweet {

    @Id
    private String messageId;

    @EqualsAndHashCode.Exclude
    private Date createdAt;

    @EqualsAndHashCode.Exclude
    private String text;

    @EqualsAndHashCode.Exclude
    private Long quoteCount;

    @EqualsAndHashCode.Exclude
    private Long replyCount;

    @EqualsAndHashCode.Exclude
    private Long retweetCount;

    @EqualsAndHashCode.Exclude
    private Long favoriteCount;


    @EqualsAndHashCode.Exclude
    private String authorId;

    @EqualsAndHashCode.Exclude
    private Date authorCreatedAt;

    @EqualsAndHashCode.Exclude
    private String authorScreenName;

    @EqualsAndHashCode.Exclude
    private Long authorFollowersCount;

    @EqualsAndHashCode.Exclude
    private Long authorStatusesCount;

}
