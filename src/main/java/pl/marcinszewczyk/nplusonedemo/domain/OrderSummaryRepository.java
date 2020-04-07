package pl.marcinszewczyk.nplusonedemo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderSummaryRepository extends JpaRepository<OrderSummary, Long> {
    @Query("select distinct s from OrderSummary s join fetch s.items where s.userId = ?1")
    List<OrderSummary> findByUserId(Long userId);
}
