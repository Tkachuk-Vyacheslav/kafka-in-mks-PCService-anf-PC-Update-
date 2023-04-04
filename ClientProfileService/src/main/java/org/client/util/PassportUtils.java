package org.client.util;

import lombok.AllArgsConstructor;
import org.client.common.dto.IndividualDto;
import org.client.common.dto.RFPassportDto;
import org.client.common.entity.Individual;
import org.client.common.entity.RFPassport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PassportUtils {
    private  ModelMapper modelMapper;

    public RFPassport convertToEntity(RFPassportDto rfPassportDto) {

        RFPassport rfPassport = modelMapper.map(rfPassportDto, RFPassport.class);
        //Individual individual = modelMapper.map(individualDto, Individual.class);
        return rfPassport;
    }

    public  RFPassportDto convertToDto(RFPassport rfPassport) {
        RFPassportDto rfPassportDto = modelMapper.map(rfPassport, RFPassportDto.class);
        //IndividualDto individualDto = modelMapper.map(individual, IndividualDto.class);
        return rfPassportDto;
    }
}
