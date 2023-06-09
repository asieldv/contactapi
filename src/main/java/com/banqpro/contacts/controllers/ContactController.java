package com.banqpro.contacts.controllers;

import com.banqpro.contacts.dto.ContactDto;
import com.banqpro.contacts.entities.Contact;
import com.banqpro.contacts.enums.StatusResponse;
import com.banqpro.contacts.services.ContactService;
import com.banqpro.contacts.utils.mapperUtils;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.websocket.server.ServerEndpoint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

@RestController
@ServerEndpoint(value = "/api")
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    private SimpleDateFormat dateFormat;

    @PostConstruct
    public void onBuild() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @ApiOperation(value = "Add contact to database.")
    @PostMapping(value = "/addcontact", consumes = {"application/json"})
    public ResponseEntity<HttpStatus> addContact(@RequestBody ContactDto contactDto) {
        Contact contact = mapperUtils.getModelMapperInstance().map(contactDto, Contact.class);
        boolean wasAdded = contactService.addContact(contact);
        return new ResponseEntity((wasAdded) ? StatusResponse.OK.name(): StatusResponse.INTERNAL_SERVER_ERROR.name(), HttpStatus.OK);
    }

    @ApiOperation(value = "Update contact, you must provide as a reference the old phone number and the new data to update.")
    @PostMapping(value = "/updatecontact")
    public ResponseEntity<HttpStatus> updateContact(@RequestParam("firstname") String firstName, @RequestParam("secondname") String secondName,
                                                        @RequestParam("address") String address, @RequestParam("dateofbirth") String dateOfBirth,
                                                        @RequestParam("oldnumber") String oldNumber, @RequestParam("phonenumber") String phoneNumber,
                                                        @RequestParam("photo") byte[] photo) throws ParseException {
        Date date = dateFormat.parse(dateOfBirth);
        boolean wasUpdated = contactService.updateContact(oldNumber, new ContactDto(firstName, secondName, address, date, phoneNumber, photo));
        return new ResponseEntity((wasUpdated) ? StatusResponse.OK.name() : StatusResponse.NOT_FOUND.name(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete contact, you must provide the phone number.")
    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteContact(@RequestParam("phonenumber") String phoneNumber) {
        contactService.deleteContactByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Find contact by phone number.", response = ContactDto.class)
    @GetMapping("/findbyphonenumber")
    public ResponseEntity<ContactDto> findContact(@RequestParam("phonenumber") String phoneNumber) {
        Contact contact = contactService.findContactByPhoneNumber(phoneNumber);
        ContactDto contactDto = null;
        if (Objects.nonNull(contact))
            contactDto = mapperUtils.getModelMapperInstance().map(contact, ContactDto.class);
        return new ResponseEntity<>(contactDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Find contacts by first name.", response = Collection.class)
    @GetMapping("/findbyfirstname")
    public ResponseEntity<Collection<ContactDto>> findByFirstName(@RequestParam("firstname") String firstName) {
        Collection<Contact> contacts = contactService.findContactByFirstName(firstName);
        Collection<ContactDto> contactDtos = null;
        if (!contacts.isEmpty())
            contactDtos = mapperUtils.mapContacts(contacts);
        return new ResponseEntity<>(contactDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Find all contacts.", response = Collection.class)
    @GetMapping("/findall")
    public ResponseEntity<Collection<ContactDto>> findAll() {
        Collection<Contact> contacts = contactService.findAll();
        Collection<ContactDto> contactDtos = null;
        if (!contacts.isEmpty())
            contactDtos = mapperUtils.mapContacts(contacts);
        return new ResponseEntity<>(contactDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Find contacts hose date of birth is between two dates given as a parameter.", response = Collection.class)
    @GetMapping("/findbydaterange")
    public ResponseEntity<Collection<ContactDto>> findByDateRange(@RequestParam("initdate") @NotNull String initDate, @RequestParam("enddate") @NotNull String endDate) throws ParseException {
        Date init = dateFormat.parse(initDate);
        Date end = dateFormat.parse(endDate);
        Collection<Contact> contacts = contactService.findContactsByDateRange(init, end);
        Collection<ContactDto> contactDtos = null;
        if (!contacts.isEmpty())
            contactDtos = mapperUtils.mapContacts(contacts);
        return new ResponseEntity<>(contactDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Find contacts by name and address.", response = Collection.class)
    @GetMapping("/findbynameandaddress")
    public ResponseEntity<Collection<ContactDto>> findByNameAndAddress(@RequestParam("firstname") String firstName, @RequestParam("address") String address) {
        Collection<Contact> contacts = contactService.findByFirstNameAndAddress(firstName, address);
        Collection<ContactDto> contactDtos = null;
        if (!contacts.isEmpty())
            contactDtos = mapperUtils.mapContacts(contacts);
        return new ResponseEntity<>(contactDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Find contacts by name or address.", response = Collection.class)
    @GetMapping("/findbynameoraddress")
    public ResponseEntity<Collection<ContactDto>> findByNameOrAddress(@RequestParam("firstname") String firstName, @RequestParam("address") String address) {
        Collection<Contact> contacts = contactService.findByFirstNameOrAddress(firstName, address);
        Collection<ContactDto> contactDtos = null;
        if (!contacts.isEmpty())
           contactDtos = mapperUtils.mapContacts(contacts);
        return new ResponseEntity<>(contactDtos, HttpStatus.OK);
    }

}
