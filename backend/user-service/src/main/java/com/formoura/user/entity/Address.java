package com.formoura.user.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name="user_address")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String fullName;

    private String mobile;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    private boolean defaultAddress;

}