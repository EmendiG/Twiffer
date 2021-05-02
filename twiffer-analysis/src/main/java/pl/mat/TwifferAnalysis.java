package pl.mat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mat.service.KafkaConsumerService;

@SpringBootApplication
public class TwifferAnalysis  implements CommandLineRunner {

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    public static void main(String[] args) {
        SpringApplication.run(TwifferAnalysis.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        kafkaConsumerService.run();
    }

}
