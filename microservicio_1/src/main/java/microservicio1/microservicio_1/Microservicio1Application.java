package microservicio1.microservicio_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "microservicio1.microservicio_1.model")
@SpringBootApplication
public class Microservicio1Application {

	public static void main(String[] args) {
		SpringApplication.run(Microservicio1Application.class, args);
	}

}
 