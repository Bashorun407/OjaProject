//import com.akinovapp.email.mimemail.MimeMail;
import com.akinovapp.email.simplemail.SimpleMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.akinovapp.*"})
@EntityScan(basePackages = {"com.akinovapp.*"})
@EnableJpaRepositories(basePackages = {"com.akinovapp.*"})
public class OjaApp {

    @Autowired
    private SimpleMail simpleMail;

//    @Autowired
//    private MimeMail mimeMail;

    public static void main(String[] args) {
        SpringApplication.run(OjaApp.class);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void triggerMail(){

        simpleMail.simpleMailSender("akinolusheyi@gmail.com", "SEASONS GREETINGS",
                "Hello Friends, how have you been? Its another Yuletide season and this is a call to celebrate goodness.");
    }

//Its like there can not be two @Event Listeners in the application
//    @EventListener(ApplicationReadyEvent.class)
//    public void sendMimeMail() throws MessagingException {
//
//        mimeMail.sendMimeMail("olusheyi@gmail.com", "NEW YEAR GREETINGS",
//                "Hello colleagues...its going to be a greater year of new possibilities", "C:\\Users\\Akinbobola Oluwaseyi\\Desktop\\Training Pictures");
//    }


}
