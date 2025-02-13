package com.aa.firstquestion.kafka;

import com.aa.firstquestion.dto.DeliveredOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderKafkaProducer {

    private final KafkaTemplate<String, DeliveredOrder> kafkaTemplate;
    private static final String TOPIC = "order_delivery_statistics";

    public void publishOrder(DeliveredOrder deliveredOrder) {
        kafkaTemplate.send(TOPIC, deliveredOrder);
    }
}
