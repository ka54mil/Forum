package com.example.auction.models;

import com.example.auction.validators.annotations.UniqueUsername;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@UniqueUsername
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 36)
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    @Transient
    private String passwordConfirm;

    @Size(min = 4, max = 36)
    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "enabled")
    private boolean enabled = true;

    @AssertTrue
    private boolean isPasswordsEquals(){
        return password == null || passwordConfirm == null || password.equals(passwordConfirm);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(String username){
        this(username, true);
    }

    public User(String username, boolean enabled){
        this.username = username;
        this.enabled = enabled;
    }

}