package EduStore_Pet.Pet_pr.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic userRegistrationTopic(){
        return TopicBuilder.name("user-registration")
                .partitions(3) // разделение данных на 3 партиции для параллельной обработки
                .replicas(1) // обеспечивает надежность и доступность данных
                .build();
    }
}
