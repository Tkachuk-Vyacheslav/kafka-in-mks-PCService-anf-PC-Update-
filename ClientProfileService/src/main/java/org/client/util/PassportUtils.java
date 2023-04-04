package org.client.util;

import lombok.AllArgsConstructor;
import org.client.common.dto.RFPassportDto;
import org.client.common.entity.RFPassport;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PassportUtils {

    private  ModelMapper modelMapper;

    public RFPassport convertToEntity(RFPassportDto rfPassportDto) {
        RFPassport rfPassport = modelMapper.map(rfPassportDto, RFPassport.class);
        return rfPassport;
    }

    public  RFPassportDto convertToDto(RFPassport rfPassport) {
        RFPassportDto rfPassportDto = modelMapper.map(rfPassport, RFPassportDto.class);
        return rfPassportDto;
    }
}
