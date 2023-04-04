package org.client.util;

import lombok.AllArgsConstructor;
import org.client.common.dto.EmailDto;
import org.client.common.entity.Contacts.Email;
import org.modelmapper.ModelMapper;
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
        return emailDto;
    }

}
