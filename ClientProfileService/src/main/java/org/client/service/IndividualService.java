package org.client.service;

import org.client.common.dto.IndividualDto;
import org.client.common.entity.Individual;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public interface IndividualService {

    IndividualDto getClientByPasspUuid(String passpUuid);

    IndividualDto getClient(String icp);

    IndividualDto getClientByPhoneNum(String value);

    IndividualDto getClientByWalletUuid (String uuid);

    List<IndividualDto> getAll();

    void editClient(IndividualDto dto, String ClientUuid);

    void deleteIndivid(String clientFromBodyUuid, String uuidFromParam);

    void updateClientIfArchived(IndividualDto individual);

    void checkIsArchived(IndividualDto individualDto);

    void addClient(IndividualDto individualDto);
}
