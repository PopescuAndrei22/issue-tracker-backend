package com.example.issuetracker.domain.entities;

import com.example.issuetracker.domain.models.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name  = "users")
public class User extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    protected User() {}

    public User(String username, String password, String email, Role role) {

        this.username = validateUsername(username);
        this.password = validatePassword(password);
        this.email = validateEmail(email);
        this.role = validateRole(role);
    }

    private String validateUsername(String username){
        if(username == null || username.isBlank()){
            throw new IllegalArgumentException("The username cannot be blank");
        }

        return username;
    }

    private String validateEmail(String email){
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("The email cannot be blank");
        }

        return email;
    }

    private String validatePassword(String password){
        if(password == null || password.isBlank()){
            throw new IllegalArgumentException("The password cannot be blank");
        }
        return password;
    }

    private Role validateRole(Role role){
        if(role == null){
            throw new IllegalArgumentException("The role must be provided");
        }
        return role;
    }

    public void changeEmail(String email){
        this.email = validateEmail(email);
    }

    public void changeRole(Role role){
        this.role = validateRole(role);
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public Role getRole(){
        return this.role;
    }

    public String getPassword(){
        return this.password;
    }

    public Long getId(){
        return id;
    }
}
