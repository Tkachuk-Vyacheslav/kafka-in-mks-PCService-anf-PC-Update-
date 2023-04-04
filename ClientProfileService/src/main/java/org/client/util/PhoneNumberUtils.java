package org.client.util;

import lombok.AllArgsConstructor;
import org.client.common.dto.PhoneNumberDto;
import org.client.common.entity.Contacts.PhoneNumber;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PhoneNumberUtils {

    private  ModelMapper modelMapper;

    public PhoneNumber convertToEntity(PhoneNumberDto phoneNumberDto) {
        PhoneNumber phoneNumber = modelMapper.map(phoneNumberDto, PhoneNumber.class);
        return phoneNumber;
    }

    public  PhoneNumberDto convertToDto(PhoneNumber phoneNumber) {
        PhoneNumberDto phoneNumberDto = modelMapper.map(phoneNumber, PhoneNumberDto.class);
        return phoneNumberDto;
    }

}
