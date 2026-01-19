package com.sportisfun.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "app_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Size(max=30, message = "Username should contain max 30 characters")
    @Column(unique = true, nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Email
    @Column(unique = true, nullable = false, length = 100)
    @JsonIgnore
    private String email;

    @Size(min = 8)
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal balance =  BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Role role;

    @OneToMany(
            mappedBy = "user"
    )
    private List<BetSlip> betSlips;
}
