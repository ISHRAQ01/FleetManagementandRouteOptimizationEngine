package com.project.FleetManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.NIP;

import java.time.LocalDateTime;

@Entity
@Table(name="USERS")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true,nullable=false)
    private String username;
    @Column(unique=true,nullable=false)
    private String password;

    private String role;
    private LocalDateTime createdAt;

    public User(String userName, String password, String role) {
        this.username = userName;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

}
