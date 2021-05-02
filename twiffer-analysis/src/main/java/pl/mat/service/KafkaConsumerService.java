package pl.mat.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Service
public class KafkaConsumerService {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class.getName());
    private final KafkaConsumer<String, String> kafkaConsumer;
    private final List<String> topics;

    @Autowired
    public KafkaConsumerService(KafkaConsumer<String, String> kafkaConsumer,
                                @Value("${spring.kafka.template.default-topic}") String[] topics) {
        this.kafkaConsumer = kafkaConsumer;
        this.topics = Arrays.asList(topics);
    }


    public void run(){
        kafkaConsumer.subscribe(topics);
        while(true){
            ConsumerRecords<String, String> records =
                    kafkaConsumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records){
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Partition: " + record.partition() + ", Offset:" + record.offset());
            }
        }
    }

}
