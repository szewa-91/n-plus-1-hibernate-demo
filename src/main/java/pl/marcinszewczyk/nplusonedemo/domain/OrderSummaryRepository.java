package pl.marcinszewczyk.nplusonedemo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderSummaryRepository extends JpaRepository<OrderSummary, Long> {
    List<OrderSummary> findByUserId(Long userId);
}
