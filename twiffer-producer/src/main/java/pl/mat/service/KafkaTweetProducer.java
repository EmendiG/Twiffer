package pl.mat.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaTweetProducer {

    Logger logger = LoggerFactory.getLogger(KafkaTweetProducer.class.getName());

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    public void send(String topic, String payload) {
        kafkaProducer.send(
                new ProducerRecord<>(topic, null, payload),
                (recordMetadata, e) -> {
                    if (e != null) {
                        logger.error("Something bad happened", e);
                    }
                }
        );
    }

}
