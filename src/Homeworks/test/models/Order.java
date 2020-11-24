package Homeworks.test.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double orderPrice;

    @Column
    private Date orderDate;

    @Column(name = "client_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Client client;

    public Order() {
    }
}
