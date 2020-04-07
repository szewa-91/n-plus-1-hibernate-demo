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

import javax.persistence.*;
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

    PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();

    @Test
    void shouldExtractItemsFromOrders() {
        List<OrderSummary> summaries = orderSummaryRepository.findByUserId(1L);

        // items are not loaded by Hibernate yet
        assertThat(persistenceUtil.isLoaded(summaries.get(0).getItems())).isEqualTo(false);
        assertThat(persistenceUtil.isLoaded(summaries.get(1).getItems())).isEqualTo(false);
        assertThat(persistenceUtil.isLoaded(summaries.get(2).getItems())).isEqualTo(false);
        assertThat(persistenceUtil.isLoaded(summaries.get(3).getItems())).isEqualTo(false);
        assertThat(persistenceUtil.isLoaded(summaries.get(4).getItems())).isEqualTo(false);

        // referencing to items from the first order summary
        System.out.println(summaries.get(0).getItems());

        // only items from two first summaries are loaded (by one query)
        assertThat(persistenceUtil.isLoaded(summaries.get(0).getItems())).isEqualTo(true);
        assertThat(persistenceUtil.isLoaded(summaries.get(1).getItems())).isEqualTo(true);
        assertThat(persistenceUtil.isLoaded(summaries.get(2).getItems())).isEqualTo(false);
        assertThat(persistenceUtil.isLoaded(summaries.get(3).getItems())).isEqualTo(false);
        assertThat(persistenceUtil.isLoaded(summaries.get(4).getItems())).isEqualTo(false);

        // referencing items from the third summary
        System.out.println(summaries.get(2).getItems());

        // only items from two first summaries are loaded (by one query)
        assertThat(persistenceUtil.isLoaded(summaries.get(0).getItems())).isEqualTo(true);
        assertThat(persistenceUtil.isLoaded(summaries.get(1).getItems())).isEqualTo(true);
        assertThat(persistenceUtil.isLoaded(summaries.get(2).getItems())).isEqualTo(true);
        assertThat(persistenceUtil.isLoaded(summaries.get(3).getItems())).isEqualTo(true);
        assertThat(persistenceUtil.isLoaded(summaries.get(4).getItems())).isEqualTo(false);


        //with OrderSummary::getItems we force Hibernate to load the underlying items into memory
        List<Item> items = summaries.stream()
                .map(OrderSummary::getItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        //now all items should be loaded
        assertThat(persistenceUtil.isLoaded(summaries.get(0).getItems())).isEqualTo(true);
        assertThat(persistenceUtil.isLoaded(summaries.get(1).getItems())).isEqualTo(true);
        assertThat(persistenceUtil.isLoaded(summaries.get(2).getItems())).isEqualTo(true);
        assertThat(persistenceUtil.isLoaded(summaries.get(3).getItems())).isEqualTo(true);
        assertThat(persistenceUtil.isLoaded(summaries.get(4).getItems())).isEqualTo(true);
        assertThat(items).hasSize(8);
    }
}
