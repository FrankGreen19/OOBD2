package Homeworks.Lab8.domain;

import javax.persistence.*;

@Entity
@Table(name = "person")
public class Worker extends Person implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String position;

    public Worker(String email, String password, String phoneNumber, String firstName, String lastName, String position) {
        super(email, password, phoneNumber, firstName, lastName);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
