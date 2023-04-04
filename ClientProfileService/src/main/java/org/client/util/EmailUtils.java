package org.client.util;

import lombok.AllArgsConstructor;
import org.client.common.dto.EmailDto;
import org.client.common.dto.IndividualDto;
import org.client.common.dto.RFPassportDto;
import org.client.common.entity.Contacts.Email;
import org.client.common.entity.Individual;
import org.client.common.entity.RFPassport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailUtils {
    private  ModelMapper modelMapper;

    public Email convertToEntity(EmailDto emailDto) {

        Email email = modelMapper.map(emailDto, Email.class);

        return email;
    }

    public  EmailDto convertToDto(Email email) {
        EmailDto emailDto = modelMapper.map(email, EmailDto.class);
        //IndividualDto individualDto = modelMapper.map(individual, IndividualDto.class);
        return emailDto;
    }

}
