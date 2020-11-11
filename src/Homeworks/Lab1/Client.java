package Homeworks.Lab1;

import org.postgresql.util.PGobject;
import org.postgresql.util.PGtokenizer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
public class Client extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int discount;

    @OneToMany
    private List<Order> orderList;

    public Client(String email, String password, String phoneNumber, String firstName, String lastName) {
        super(email, password, phoneNumber, firstName, lastName);
    }

    public Client() {
        super();
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    //    @Column
//    private List<Order> orderList;

//    public List<Order> getOrderList() {
//        return orderList;
//    }
//
//    public void setOrderList(List<Order> orderList) {
//        this.orderList = orderList;
//    }

//    public String getSqlArray() {
//        StringBuilder s= new StringBuilder("{\"");
//        for (Order order: orderList) {
//            s.append(order.getValue()).append("\",\"");
//        }
//        s = new StringBuilder(s.substring(0, s.length() - 2));
//        s.append("}");
//        return s.toString();
//    }
//
//    public void showOrderList() {
//        for (Order order: this.orderList) {
//            System.out.println(order.toString());
//        }
//    }

    public void setValue(String value) {
        String s = value.substring(1, value.length()-1);
        PGtokenizer t = new PGtokenizer(s, ',');
        String s2 = t.getToken(0).substring(2, t.getToken(0).length() - 2);
        String[] array = s2.split(",");
        this.setEmail(array[0]);
        this.setPassword(array[1]);
        this.setPhoneNumber(array[2]);
        this.setFirstName(array[3]);
        this.setLastName(array[4]);
        this.discount = Integer.parseInt(t.getToken(1));
        System.out.println(this.discount);
    }

    @Override
    public String getValue() {
        return "('" + this.getEmail() + "','" + this.getPassword() + "','" + this.getPhoneNumber() + "','"
                + this.getFirstName() + "','" + this.getLastName() + "')";
    }
}
