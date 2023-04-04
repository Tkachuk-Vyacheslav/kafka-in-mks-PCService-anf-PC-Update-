package org.client.util;

import lombok.AllArgsConstructor;
import org.client.common.dto.IndividualDto;
import org.client.common.dto.PhoneNumberDto;
import org.client.common.dto.RFPassportDto;
import org.client.common.entity.Contacts.PhoneNumber;
import org.client.common.entity.Individual;
import org.client.common.entity.RFPassport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PhoneNumberUtils {
    private  ModelMapper modelMapper;

    public PhoneNumber convertToEntity(PhoneNumberDto phoneNumberDto) {

        PhoneNumber phoneNumber = modelMapper.map(phoneNumberDto, PhoneNumber.class);
        //Individual individual = modelMapper.map(individualDto, Individual.class);
        return phoneNumber;
    }

    public  PhoneNumberDto convertToDto(PhoneNumber phoneNumber) {
        PhoneNumberDto phoneNumberDto = modelMapper.map(phoneNumber, PhoneNumberDto.class);
        //IndividualDto individualDto = modelMapper.map(individual, IndividualDto.class);
        return phoneNumberDto;
    }


}
