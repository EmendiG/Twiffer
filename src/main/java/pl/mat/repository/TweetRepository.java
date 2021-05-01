package pl.mat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.mat.model.db.Tweet;

@EnableMongoRepositories
public interface TweetRepository extends MongoRepository<Tweet, Long> {

}
