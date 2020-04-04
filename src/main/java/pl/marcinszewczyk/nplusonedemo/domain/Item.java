package pl.marcinszewczyk.nplusonedemo.domain;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Item {
    @Id
    @SequenceGenerator(name = "itemSeq", initialValue = 101)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemSeq")
    Long id;

    String name;
    BigDecimal price;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
