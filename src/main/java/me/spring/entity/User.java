package me.spring.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private int house_number;
    private int quantity_car;

    @JsonIgnore
    private String password;

    @ManyToMany
    @JoinTable(name="USER_ROLES",
            joinColumns=@JoinColumn(name="USER_ID"),
            inverseJoinColumns=@JoinColumn(name="ROLES_ID")
    )
    private List<Role> roles;

    public User(String name, String email) {
        super();
        this.name = name;
        this.email = email;
    }
    public User(User user) {
        super();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }
    public User(String name, String email, String password, List<Role> roles) {
        super();
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }
}
