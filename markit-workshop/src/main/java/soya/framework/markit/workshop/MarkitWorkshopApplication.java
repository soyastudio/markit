package soya.framework.markit.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class MarkitWorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarkitWorkshopApplication.class, args);
    }

    @EventListener(classes = {ApplicationReadyEvent.class})
    public void onApplicationEvent(ApplicationReadyEvent event) throws Exception {
        ApplicationContext context = event.getApplicationContext();

    }
}
