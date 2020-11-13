package Homeworks.Lab1;

import Homeworks.Lab4.DatabaseHandler;
import Homeworks.Lab5.Lab5ProductService;
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
public class Order implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double orderPrice;

    @Column
    private Date orderDate;

    @Column
    private Long clientID;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productList;

    public Order(double orderPrice, Date orderDate, long clientID, List<Product> productList) {
        this.orderPrice = orderPrice;
        this.orderDate = orderDate;
        this.clientID = clientID;
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

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
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
                "\nclientID: " + this.clientID +
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

    public void setValue(String value) throws SQLException, ParseException {
        Connection connection = DatabaseHandler.getDbConnection();
        String s = value.substring(1, value.length()-1);
        PGtokenizer t = new PGtokenizer(s, ',');
        this.orderPrice = Double.parseDouble(t.getToken(0));
        this.orderDate = new SimpleDateFormat("dd-MM-yyyy").parse(t.getToken(1));
        this.clientID = Long.parseLong(t.getToken(2));
//        java.sql.Array sqlArray = connection.createArrayOf("product", new String[]{t.getToken(3)});
//        Object array = sqlArray.getArray();
//        System.out.println(array.toString());
////        for (int i = 3; i < t.getSize(); i++) {
//////            Product product = new Product();
//////            product.setValue(t.getToken(i));
//////            this.productList.add(product);
//////            System.out.println(t.getToken(i));
////            String s2 = value.substring(1, t.getToken(3).length()-1);
////            PGtokenizer t2 = new PGtokenizer(s2, ',');
////            System.out.println(t.getToken(3));
////        }
    }

//    public String getSqlProducts() {
//        StringBuilder s = new StringBuilder("{\"");
//        for (Product product: productList) {
//            s.append(product.getValue()).append("\",\"");
//        }
//        s = new StringBuilder(s.substring(0, s.length() - 2));
//        s.append("}");
//
//        return String.valueOf(s);
//    }

    public String getValue() {
        StringBuilder s= new StringBuilder("{\"");
        for (Product product: productList) {
            s.append(product.getValue()).append("\",\"");
        }
        s = new StringBuilder(s.substring(0, s.length() - 2));
        s.append("}");
        return "('" + orderPrice + "','" + orderDate + "','" + clientID + "'," + s +")";
    }
}
