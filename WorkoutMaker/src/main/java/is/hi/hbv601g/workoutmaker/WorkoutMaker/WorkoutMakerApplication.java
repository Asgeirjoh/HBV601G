package is.hi.hbv601g.workoutmaker.WorkoutMaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class WorkoutMakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkoutMakerApplication.class, args);
	}

}
