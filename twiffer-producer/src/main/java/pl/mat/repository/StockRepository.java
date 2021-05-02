package pl.mat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.mat.model.db.Bitcoin;

@EnableMongoRepositories
public interface StockRepository extends MongoRepository<Bitcoin, Long> {

}
