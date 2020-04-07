package pl.marcinszewczyk.nplusonedemo.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderSummary {
    @Id
    @SequenceGenerator(name = "orderSeq", initialValue = 101)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeq")
    Long id;

    Long userId;

    String name;

    @OneToMany()
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "SUMMARY_ID")
    List<Item> items = new ArrayList<>();

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }
}
