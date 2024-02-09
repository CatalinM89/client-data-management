package com.example.demo.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "first_name", "last_name" }) })
public final class Customer extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column
    private String email;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "houseNumber", column = @Column(name = "house_number")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code"))
    })
    private Address currentLivingAddress;

    @Version
    private Long version;

}
