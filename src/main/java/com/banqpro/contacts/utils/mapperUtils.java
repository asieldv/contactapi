package com.banqpro.contacts.utils;

import com.banqpro.contacts.dto.ContactDto;
import com.banqpro.contacts.entities.Contact;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class mapperUtils {

    private static ModelMapper modelMapper;

    public static ModelMapper getModelMapperInstance() {
        return (Objects.isNull(modelMapper)) ? modelMapper = new ModelMapper() : modelMapper;
    }

    public static Collection<ContactDto> mapContacts(@NotNull Collection<Contact> contacts) {
        return contacts.stream().map(
                contact -> getModelMapperInstance().map(contact, ContactDto.class)
        ).collect(Collectors.toList());
    }
}
