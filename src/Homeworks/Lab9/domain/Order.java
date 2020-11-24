package Homeworks.Lab9.domain;

import Homeworks.Lab4.DatabaseHandler;
import org.postgresql.util.PGtokenizer;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order")
public class Order implements Comparable, BaseEntity {

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productList;

    public Order(double orderPrice, Date orderDate, Client client, List<Product> productList) {
        this.orderPrice = orderPrice;
        this.orderDate = orderDate;
        this.client = client;
        this.productList = productList;
    }

    public Order(){}

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void showProductsList() {
        for (Product product: this.productList) {
            System.out.println(product.toString());
        }
    }

    @Override
    public String toString() {
        return "Order: \n" +
                "\norderPrice: " + this.orderPrice +
                "\nclient: " + this.client.toString() +
                "\norderDate: " + this.orderDate;
    }

    @Override
    public int compareTo(Object obj) {
        Order tmp = (Order) obj;
        if (this.getOrderPrice() < tmp.getOrderPrice())
        {
            return -1;
        }
        else if(this.orderPrice > tmp.orderPrice)
        {
            return 1;
        }
        return 0;
    }


    public String getValue() {
        StringBuilder s= new StringBuilder("{\"");
        for (Product product: productList) {
            s.append(product.getValue()).append("\",\"");
        }
        s = new StringBuilder(s.substring(0, s.length() - 2));
        s.append("}");
        return "('" + orderPrice + "','" + orderDate + "','" + "'," + s +")";
    }
}
