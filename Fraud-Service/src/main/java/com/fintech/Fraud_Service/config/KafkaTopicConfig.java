package com.fintech.Fraud_Service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic fraudAlertTopic() {
        return new NewTopic("fraud.alerts", 1, (short) 1);
    }
}
