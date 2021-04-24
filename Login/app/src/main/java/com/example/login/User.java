package com.example.login;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;

    public User(int id, String name, String username, String password, String email, String phone) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}
