package pl.mat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.mat.model.db.Tweet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@EnableMongoRepositories
public interface TweetRepository extends MongoRepository<Tweet, Long> {

    Optional<List<Tweet>> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
