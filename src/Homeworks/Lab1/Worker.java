package Homeworks.Lab1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Worker extends Person {

    @Id
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
