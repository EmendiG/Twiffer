package pl.mat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.mat.service.RunningService;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class TwifferApplication implements CommandLineRunner {

	private final RunningService runningService;

	public TwifferApplication(RunningService runningService) {
		this.runningService = runningService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TwifferApplication.class, args);
	}

	@Override
	public void run(String... args) throws InterruptedException {
		runningService.startProcess();
	}

}
