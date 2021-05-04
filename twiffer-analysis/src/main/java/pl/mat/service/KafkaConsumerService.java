package pl.mat.service;

import com.google.common.collect.EvictingQueue;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.mat.analyser.TweetAnalyser;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

@Service
public class KafkaConsumerService {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class.getName());
    private final KafkaConsumer<String, String> kafkaConsumer;
    private final TweetAnalyser tweetAnalyser;
    private final List<String> topics;
    private final Queue<String> recentDates;

    @Autowired
    public KafkaConsumerService(KafkaConsumer<String, String> kafkaConsumer,
                                TweetAnalyser tweetAnalyser,
                                @Value("${spring.kafka.template.default-topic}") String[] topics) {
        this.recentDates = EvictingQueue.create(10);
        this.kafkaConsumer = kafkaConsumer;
        this.tweetAnalyser = tweetAnalyser;
        this.topics = Arrays.asList(topics);
    }

    public void run(){
        kafkaConsumer.subscribe(topics);
        while(true){
            ConsumerRecords<String, String> records =
                    kafkaConsumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records){

                if (!recentDates.contains(record.value())) {
                    this.recentDates.add(record.value());
                    LocalDateTime receivedDateTime = LocalDateTime.parse(record.value(), DateTimeFormatter.ISO_DATE_TIME);
                    tweetAnalyser.analyseTweetsForGivenDateTime(receivedDateTime);
                }
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Partition: " + record.partition() + ", Offset:" + record.offset());
            }
        }
    }

}
