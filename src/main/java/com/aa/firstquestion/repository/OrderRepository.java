package com.aa.firstquestion.repository;
import com.aa.firstquestion.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByDeliveredAtBetween(LocalDateTime start, LocalDateTime end);
}
