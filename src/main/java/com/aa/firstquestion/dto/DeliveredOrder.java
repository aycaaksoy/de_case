package com.aa.firstquestion.dto;
import com.aa.firstquestion.model.Order;
import lombok.*;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveredOrder {

    private Long id;
    private String createdAt;
    private String lastUpdatedAt;
    private Integer collectionDuration;
    private Integer deliveryDuration;
    private Integer eta;
    private Integer leadTime;
    private Boolean orderInTime;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static DeliveredOrder fromOrder(Order order) {
        return DeliveredOrder.builder()
                .id(order.getId())
                .createdAt(formatDate(order.getCreatedAt()))
                .lastUpdatedAt(formatDate(order.getLastUpdatedAt()))
                .collectionDuration(calculateDuration(order.getCollectionStartedAt(), order.getCollectedAt()))
                .deliveryDuration(calculateDuration(order.getDeliveryStartedAt(), order.getDeliveredAt()))
                .eta(order.getEta())
                .leadTime(calculateDuration(order.getCreatedAt(), order.getDeliveredAt()))
                .orderInTime(isOrderInTime(order))
                .build();
    }

    private static Boolean isOrderInTime(Order order) {
        if (order.getDeliveredAt() == null || order.getEta() == null || order.getCreatedAt() == null) {
            return null;
        }
        int leadTime = calculateDuration(order.getCreatedAt(), order.getDeliveredAt());
        return leadTime <= order.getEta();
    }

    private static String formatDate(java.time.LocalDateTime dateTime) {
        return (dateTime != null) ? dateTime.format(FORMATTER) : null;
    }

    private static Integer calculateDuration(java.time.LocalDateTime start, java.time.LocalDateTime end) {
        return (start != null && end != null) ? (int) Duration.between(start, end).toMinutes() : null;
    }


}
