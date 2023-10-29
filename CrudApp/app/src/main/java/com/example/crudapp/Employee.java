package com.example.crudapp;

import java.io.Serializable;
//implements Serializable to be able to pass it as an extra
public class Employee implements Serializable {

    private String firstname;
    private String lastname;
    private String username; //primary key
    private String password;
    private String email;
    private int age;

    //getters and setters
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    //Constructors
    public Employee() {
    }
    public Employee(String firstname, String lastname, String username, String password, String email, int age){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
    }
}
