package com.bmc.appointmentservice.config;

import com.bmc.appointmentservice.model.Appointment;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value(value = "${kafka.bootstrap.address}")
    private String bootStrapAddress;


    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String,Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public ProducerFactory<String, Appointment> producerFactory(){
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        JsonSerializer jsonSerializer = new JsonSerializer();
        jsonSerializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory<>(configProps,new StringSerializer(),jsonSerializer);
    }

    @Bean
    public KafkaTemplate<String, Appointment> kafkaTemplate(){
        return new KafkaTemplate<String, Appointment>(producerFactory());
    }
}
