package com.proton.learning.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity @Getter @Setter
@Accessors(chain = true)
@Table(name = "employee")
public class Employee {
    @Id @GeneratedValue
    private Integer id;
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private String roles;
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime lastModifiedTime;
    private Boolean isActivated = true;
    private Boolean isDeleted = false;
}
