package com.example.learningspring1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities") // Matches the table name
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String authority;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User user; // Assuming you have a User entity
}
