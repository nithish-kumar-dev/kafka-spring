package com.learn.spring.service;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.learn.spring.model.Course;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, Course> KafkaTemplate;

    // @KafkaListener(topics = "test-topic", groupId = "test-group")
    // public void consumeMessage(Course course) {
    //     KafkaTemplate.send("course", course);
    //     System.out.println("Consumed message from Kafka: " + course);
    //     // Logic to consume a message from Kafka
    // }

    public void consumeKafkaData() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            String topic = "test-topic1";
            String key = "topic";

            // Added <String, String> generics to prevent raw type warnings
            consumer.subscribe(Collections.singletonList(topic));
            System.out.println("Subscribed to topic: " + topic+ " going to consume messages...");
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Consumed message: " + record.value() + " from topic: " + record.topic());
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String consumeMessage() {
        // Logic to consume a message from Kafka
        return "Consumed message from Kafka";
    }
}
