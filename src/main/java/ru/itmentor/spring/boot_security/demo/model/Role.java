package ru.itmentor.spring.boot_security.demo.model;


import lombok.Data;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name= "role")
public class Role implements GrantedAuthority{

    @Id
    @Column(name="role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Transient
    private Set<User> userSet=new HashSet<>();

    public Role(String name,Long id) {
        this.name = name;
        this.id = id;
    }

    public Role() {
    }

    public Role(long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
