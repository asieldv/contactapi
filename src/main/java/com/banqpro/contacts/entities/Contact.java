package com.banqpro.contacts.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;

    @Column(unique = true)
    private String phoneNumber;

    private String firstName;

    private String secondName;

    private String address;

    private Date dateOfBirth;

    private byte[] photo;

}
