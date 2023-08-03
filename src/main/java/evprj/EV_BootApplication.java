package evprj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class EV_BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(EV_BootApplication.class, args);
		
	}

}
