package EduStore_Pet.Pet_pr.utils;

import EduStore_Pet.Pet_pr.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRegistrationListener {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "user-registration", groupId = "user-registration-group")
    public void listenToUserRegistration(String message) {
        log.info("Received message: " + message);
        try {
            String[] parts = message.split("\\|\\|");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Message format is invalid: " + message);
            }
            String adminEmail = parts[0].replace("AdminEmail:", "").trim();
            String notificationMessage = parts[1].trim();

            emailService.sendEmail(adminEmail, "New User Registration", notificationMessage);
        } catch (Exception e) {
            log.error("Error processing message: " + message, e);
        }
    }
}
