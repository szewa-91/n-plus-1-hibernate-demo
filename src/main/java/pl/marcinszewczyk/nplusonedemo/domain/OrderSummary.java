package pl.marcinszewczyk.nplusonedemo.domain;

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

    @OneToMany(fetch = FetchType.EAGER)
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
