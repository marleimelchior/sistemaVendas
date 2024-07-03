package springboot.svendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SvendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SvendasApplication.class, args);
	}

}
