package EduStore_Pet.Pet_pr.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${admin.email}")
    private String adminEmail;

    public void sendUserRegistrationNotification(String message){
        String fullMessage = "AdminEmail:" + adminEmail + "||" + message;
        kafkaTemplate.send("user-registration", fullMessage);
    }

}
