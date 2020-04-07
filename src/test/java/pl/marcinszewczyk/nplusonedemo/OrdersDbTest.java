package pl.marcinszewczyk.nplusonedemo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marcinszewczyk.nplusonedemo.domain.OrderSummary;
import pl.marcinszewczyk.nplusonedemo.domain.OrderSummaryRepository;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"pl.marcinszewczyk.nplusonedemo"})
class OrdersDbTest {

    @Autowired
    private OrderSummaryRepository orderSummaryRepository;

    PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();

    @Test
    void shouldLoadUsingSubselect() {
        List<OrderSummary> summaries = orderSummaryRepository.findByUserId(1L);

        // items are not loaded by Hibernate yet
        assertThat(persistenceUtil.isLoaded(summaries.get(0).getItems())).isEqualTo(false);
        assertThat(persistenceUtil.isLoaded(summaries.get(1).getItems())).isEqualTo(false);

        // attempting to use any of items should trigger loading all of them
        System.out.println(summaries.get(0).getItems());

        // all elements are loaded, not only a first one
        assertThat(persistenceUtil.isLoaded(summaries.get(0).getItems())).isEqualTo(true);
        assertThat(persistenceUtil.isLoaded(summaries.get(1).getItems())).isEqualTo(true);
    }
}
