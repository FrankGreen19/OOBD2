package Homeworks.Lab8.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "shop")
@XmlRootElement(name = "shop")
@Entity
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @OneToMany
    private List<Order> orders;

    @Column
    @OneToMany()
    private List<Client> clients;

    @Column
    @OneToMany()
    private List<Worker> workers;

    @Column
    @OneToMany()
    private List<Product> products;

    public Shop(String name) {
        this.name = name;
    }

    public Shop() {}

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    @XmlElementWrapper(name="products", nillable = true)
    @XmlElement(name = "product")
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getSqlOrders() {
        StringBuilder s= new StringBuilder("{\"");
        for (Order order: orders) {
            s.append(order.getValue()).append("\",\"");
        }
        s = new StringBuilder(s.substring(0, s.length() - 2));
        s.append("}");

        return String.valueOf(s);
    }

    public String getSqlProducts() {
        StringBuilder s= new StringBuilder("{\"");
        for (Product product: products) {
            s.append(product.getValue()).append("\",\"");
        }
        s = new StringBuilder(s.substring(0, s.length() - 2));
        s.append("}");

        return String.valueOf(s);
    }

    public String getSqlClients() {
        StringBuilder s= new StringBuilder("{\"");
        for (Client client: clients) {
            s.append(client.getValue() + "," + client.getDiscount()).append("\",\"");
        }
        s = new StringBuilder(s.substring(0, s.length() - 2));
        s.append("}");

        return String.valueOf(s);
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void showProducts() {
        for (Product product: this.products)
            System.out.println(product.toString());
    }
}
