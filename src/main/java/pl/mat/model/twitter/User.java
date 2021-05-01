package pl.mat.model.twitter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class User {

    private String id;

    @EqualsAndHashCode.Exclude
    private Date createdAt;

    @EqualsAndHashCode.Exclude
    private String screenName;

    @EqualsAndHashCode.Exclude
    private Long followersCount;

    @EqualsAndHashCode.Exclude
    private Long statusesCount;

}