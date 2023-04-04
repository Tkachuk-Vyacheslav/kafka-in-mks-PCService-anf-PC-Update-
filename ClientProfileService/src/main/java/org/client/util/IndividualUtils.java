package org.client.util;

import lombok.AllArgsConstructor;
import org.client.common.dto.IndividualDto;
import org.client.common.entity.Individual;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class IndividualUtils {

    private  ModelMapper modelMapper;

    public  Individual convertToEntity(IndividualDto individualDto) {
        Individual individual = modelMapper.map(individualDto, Individual.class);
        return individual;
    }

    public  IndividualDto convertToDto(Individual individual) {
        IndividualDto individualDto = modelMapper.map(individual, IndividualDto.class);
        return individualDto;
    }
}
