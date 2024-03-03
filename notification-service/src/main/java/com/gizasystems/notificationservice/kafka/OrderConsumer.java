package com.gizasystems.notificationservice.kafka;

import com.project.orderservice.event.OrderPlacedEvent;
import com.gizasystems.notificationservice.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



@Service
public class OrderConsumer {

    @Autowired
    private EmailSenderService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}",
    groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderPlacedEvent event){
        LOGGER.info(String.format("Order is received => %s", event.toString()));
        service.sendEmail(event.getEmail(), event.toString());
    }
}
