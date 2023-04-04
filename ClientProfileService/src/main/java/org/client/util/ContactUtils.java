package org.client.util;

import lombok.AllArgsConstructor;
import org.client.common.dto.ContactMediumDto;
import org.client.common.dto.IndividualDto;
import org.client.common.dto.RFPassportDto;
import org.client.common.entity.ContactMedium;
import org.client.common.entity.Individual;
import org.client.common.entity.RFPassport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ContactUtils {

    private  ModelMapper modelMapper;
    public ContactMedium convertToEntity(ContactMediumDto contactMediumDto) {

        ContactMedium contactMedium = modelMapper.map(contactMediumDto, ContactMedium.class);

        return contactMedium;
    }

    public  ContactMediumDto convertToDto(ContactMedium contactMedium) {
        ContactMediumDto contactMediumDto = modelMapper.map(contactMedium, ContactMediumDto.class);

        return contactMediumDto;
    }

}
