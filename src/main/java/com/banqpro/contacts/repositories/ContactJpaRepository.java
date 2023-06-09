package com.banqpro.contacts.repositories;

import com.banqpro.contacts.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@Repository
public interface ContactJpaRepository extends JpaRepository<Contact, Integer> {

    Collection<Contact> findByFirstName(String firstName);
    Collection<Contact> findBySecondName(String secondName);
    Contact findByPhoneNumber(String phoneNumber);
    Collection<Contact> findByDateOfBirthIsBetween(Date initDate, Date endDate);
    Collection<Contact> findByFirstNameAndAddress(String firstName, String address);
    Collection<Contact> findByFirstNameOrAddress(String firstName, String address);

    @Transactional
    @Modifying
    void deleteByPhoneNumber(String phoneNumber);
}
