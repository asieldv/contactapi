package com.banqpro.contacts.services;

import com.banqpro.contacts.dto.ContactDto;
import com.banqpro.contacts.entities.Contact;
import com.banqpro.contacts.repositories.ContactJpaRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Service
public class ContactService {

    @Autowired
    private ContactJpaRepository contactJpaRepository;

    public boolean addContact(@NotNull Contact contact) {
        Contact saved = contactJpaRepository.save(contact);
        return (Objects.isNull(saved)) ? false : true;
    }

    public Collection<Contact> findByFirstNameAndAddress(@NotNull String firstName, @NotNull String address) {
        return contactJpaRepository.findByFirstNameAndAddress(firstName, address);
    }

    public Collection<Contact> findAll() {
        return contactJpaRepository.findAll();
    }

    public Collection<Contact> findByFirstNameOrAddress(@NotNull String firstName, @NotNull String address) {
        return contactJpaRepository.findByFirstNameOrAddress(firstName, address);
    }

    public Collection<Contact> findContactsByDateRange(@NotNull Date initDate, @NotNull Date endDate) {
        return contactJpaRepository.findByDateOfBirthIsBetween(initDate, endDate);
    }

    public void deleteContactByPhoneNumber(@NotNull String phoneNumber) {
        contactJpaRepository.deleteByPhoneNumber(phoneNumber);
    }

    public Collection<Contact> findContactByFirstName(@NotNull String firstName) {
        return contactJpaRepository.findByFirstName(firstName);
    }

    public Collection<Contact> findContactBySecondName(@NotNull String secondName) {
        return contactJpaRepository.findBySecondName(secondName);
    }

    public Contact findContactByPhoneNumber(@NotNull String phoneNumber) {
        return contactJpaRepository.findByPhoneNumber(phoneNumber);
    }

    public boolean updateContact(String phoneNumber, ContactDto contactDto) {
        Contact contact = contactJpaRepository.findByPhoneNumber(phoneNumber);
        Contact updated = null;
        if(Objects.nonNull(contact)) {
            modifyContact(contact, contactDto);
            updated = contactJpaRepository.save(contact);
        }
        return (Objects.isNull(updated)) ? false : true;

    }

    private void modifyContact(Contact contact, ContactDto contactDto) {
        contact.setPhoneNumber(contactDto.getPhoneNumber());
        contact.setAddress(contactDto.getAddress());
        contact.setDateOfBirth(contactDto.getDateOfBirth());
        contact.setFirstName(contactDto.getFirstName());
        contact.setSecondName(contactDto.getSecondName());
        contact.setPhoto(contactDto.getPhoto());
    }
}
