package pl.marcinszewczyk.nplusonedemo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marcinszewczyk.nplusonedemo.domain.Item;
import pl.marcinszewczyk.nplusonedemo.domain.OrderSummary;
import pl.marcinszewczyk.nplusonedemo.domain.OrderSummaryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"pl.marcinszewczyk.nplusonedemo"})
class OrdersDbTest {

    @Autowired
    private OrderSummaryRepository orderSummaryRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void shouldExtractItemsFromOrders() {
        Collection<OrderSummary> summaries = orderSummaryRepository.findByUserId(1L);

        List<Item> items = summaries.stream()
                .map(OrderSummary::getItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertThat(items).hasSize(8);
    }

    @Test
    void shouldExtractItemsFromOrder() {
        OrderSummary summary = entityManager.find(OrderSummary.class, 2L);

        assertThat(summary.getItems()).hasSize(3);
    }
}
