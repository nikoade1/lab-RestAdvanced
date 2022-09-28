package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends RepresentationModel<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 30)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Size(min = 1, max = 50)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Min(0)
    @Column(nullable = false)
    private double money;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    public User() {
    }

    public User(String firstName, String lastName, double money) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.money = money;
    }

    public User(Long id, String firstName, String lastName, double money) {
        this();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.money = money;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public User copy() {
        return new User(this.id, this.firstName, this.lastName, this.money);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Double.compare(user.getMoney(), getMoney()) == 0
                && Objects.equals(getId(), user.getId())
                && Objects.equals(getFirstName(), user.getFirstName())
                && Objects.equals(getLastName(), user.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getFirstName(), getLastName(), getMoney());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.id +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", money=" + this.money +
                '}';
    }

}
