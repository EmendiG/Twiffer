package pl.mat.analyser;

import java.time.LocalDateTime;

public interface TweetAnalyser {
    void analyseTweetsForGivenDateTime(LocalDateTime analyzedDateTime);
}
