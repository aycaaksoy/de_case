package com.aa.firstquestion.controller;

import com.aa.firstquestion.dto.DeliveredOrder;
import com.aa.firstquestion.model.Order;
import com.aa.firstquestion.repository.OrderRepository;
import com.aa.firstquestion.kafka.OrderKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderKafkaProducer kafkaProducer;

    @GetMapping("/process/{date}")
    public List<DeliveredOrder> processOrders(@PathVariable String date) {

        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay();

        List<Order> deliveredOrders = orderRepository.findByDeliveredAtBetween(startOfDay, endOfDay);

        List<DeliveredOrder> transformedOrders = deliveredOrders.stream()
                .map(DeliveredOrder::fromOrder)
                .collect(Collectors.toList());

        transformedOrders.forEach(kafkaProducer::publishOrder);

        return transformedOrders;
    }
}
