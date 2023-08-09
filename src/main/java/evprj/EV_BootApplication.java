package evprj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class EV_BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(EV_BootApplication.class, args);
		// ConfigurableApplicationContext context = SpringApplication.run(EV_BootApplication.class, args);
	     //   SocketIOServer socketIOServer = context.getBean(SocketIOServer.class);
	     //   socketIOServer.start();
	}


}
