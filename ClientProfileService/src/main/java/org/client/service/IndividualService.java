package org.client.service;

import org.client.common.dto.IndividualDto;
import org.client.common.entity.ContactMedium;
import org.client.common.entity.Individual;
import org.client.common.entity.RFPassport;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public interface IndividualService {

    void addClient(String icp, String contactsuuid, String documentsUuid, UUID rfpasportsUuid,
                   Date birthdate, String countryOfbirth, String fullName, String gender,
                   String name, String patronymic, String placeOfBirth, String surname);


    IndividualDto getClient(String icp);

    IndividualDto getClientByPhoneNum(String value);

    List<IndividualDto> getAll();

    void editClient(String icp, Date birthdate, String countryOfbirth, String fullName, String gender,
                   String name, String patronymic, String placeOfBirth, String surname);

    void deleteIndivid(String icp);

    void updateClientIfArchived(IndividualDto individual);

    void checkIsArchived(IndividualDto individualDto);

    void addClient(IndividualDto individualDto);

    IndividualDto getClientUuid(String uuid);
}
