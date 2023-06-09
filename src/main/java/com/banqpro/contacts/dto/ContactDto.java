package com.banqpro.contacts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    private String firstName;

    private String secondName;

    private String address;

    private Date dateOfBirth;

    private String phoneNumber;

    private byte[] photo;

}
