package org.client.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client.Exception.IncorrectRequestException;
import org.client.common.dto.IndividualDto;
import org.client.common.entity.*;
import org.client.repository.IndividualRepository;
import org.client.service.IndividualService;
import org.client.util.ArchivedClientException;
import org.client.util.ClientAlreadyExistsException;
import org.client.util.IndividualUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class IndividualServiceImpl implements IndividualService {

    @Autowired
    private IndividualUtils individualUtils;
    @Autowired
    private IndividualRepository individualRepository;

    public IndividualServiceImpl() {
    }

    @Transactional
    @Override
    // добавить клиента
    public void addClient(IndividualDto individualDto) {
        Individual individual = individualUtils.convertToEntity(individualDto);
        individualRepository.save(individual);
    }

    @Override
    public IndividualDto getClientByPasspUuid(String passpUuid) {
        Individual individual = individualRepository.findCleintByPasspuuid(passpUuid);
        return individualUtils.convertToDto(individual);
    }

    @Override //получить информацию о клиенте по icp
    public IndividualDto getClient(String icp) {
        Individual i = individualRepository.findIndividualByIcp(icp).orElse(new Individual());
        return individualUtils.convertToDto(i);
    }

    @Override //получить всех клиентов
    public List<IndividualDto> getAll() {
        List<Individual> individualList = individualRepository.findAll();
        List<IndividualDto> individualDtoList = new ArrayList<>();

        // кажд элемент из individualList преобразуем в дто
        // Потом  - поместим этот объект в лист individualDtoList
        for(Individual i: individualList){
            individualDtoList.add(individualUtils.convertToDto(i));
        }
        return individualDtoList;
    }

    @Override //найти клиента (icp, name, uuid) по номеру телефона
    public IndividualDto getClientByPhoneNum(String value) {
        Individual individual = individualRepository.findByPhNum(value);
        Individual individual1 = individualRepository.findAllFieldsByUuid(individual.getUuid());
        return individualUtils.convertToDto(individual1);
    }

    @Override // найти клиента по walletUuid
    public IndividualDto getClientByWalletUuid(String walletUuid) {
        Individual individual = individualRepository.findCleintByWalletUuid(walletUuid);

        return individualUtils.convertToDto(individual);
    }

    @Transactional
    @Override  // редактировать клиента.
    public void editClient(IndividualDto dto, String clientUuid) {
        String tempUuid = dto.getUuid();
        if(!dto.getUuid().equals(clientUuid)) {
            throw new IncorrectRequestException("uuid  в теле запроса и параметрах должны быь одинаковы");
        }
        Individual userFromDB = individualRepository.findIndividualByUuid(dto.getUuid()).orElse(new Individual()); //нашли пользователя в базе по uuid client

        //переприсваиваем пользователю поля (значения из запроса в постмэне)
        userFromDB = Individual.builder().icp(dto.getIcp()).uuid(clientUuid).birthDate(dto.getBirthDate()).
                countryOfBirth(dto.getCountryOfBirth()).fullName(dto.getFullName()).gender(dto.getGender()).name(dto.getName()).
                patronymic(dto.getPatronymic()).placeOfBirth(dto.getPlaceOfBirth()).surname(dto.getSurname()).build();

        individualRepository.save(userFromDB); //сохраняем юзера (пока без uuid для контактов, документов

        // перезапишем uuid документов и контактов в табл индивидуал (если они есть). Если их не указать в дто, то в табл запишется null !!
        // если же не указать в дто клиента пасспорт, адрес и проч , то эти данные у индивидуала сохранятся (прежние)
        if(dto.getContactsUuid() != null){
            individualRepository.rewriteIndividContactUuid(dto.getContactsUuid(), dto.getUuid());
        } else if(dto.getDocumentsUuid() != null) {
            individualRepository.rewriteIndividDocumUuid(dto.getDocumentsUuid(), dto.getUuid());
        }
    }

    @Override //удалить пользователя по uuid
    public void deleteIndivid(String clientFromBodyUuid, String uuidFromParam) {
        if(!clientFromBodyUuid.equals(uuidFromParam))
            throw new IncorrectRequestException("uuid  в теле запроса и параметрах должны быtь одинаковы");

        individualRepository.deleteById(uuidFromParam);
    }

    @Transactional
    public void updateClientIfArchived(IndividualDto individual) {
        log.info(String.format("converting dto to entity %s", individual));
        Individual entity = individualUtils.convertToEntity(individual);
        RFPassport activePassport = entity.getPassport().stream().
                filter(passport -> passport.getPassportStatus().equals("active")).collect(Collectors.toList()).get(0);
        Individual archivedClient = null;
        try {
            archivedClient = individualRepository.findIndividualByIcp(individual.getIcp()).orElse(new Individual());
            if (archivedClient.getIcp().equals(individual.getIcp())) {
                throw new ClientAlreadyExistsException("Client already exists");
            }
        } catch (NullPointerException e) {
            //ignore
        }
        try {
            archivedClient = individualRepository.findByPassport(activePassport.getSeries(), activePassport.getNumber());
            archivedClient.setArchived(true);
            archivedClient.setActualIcp(individual.getIcp());
            log.info(String.format("marking archived client %s", archivedClient));
            individualRepository.save(archivedClient);
        } catch (NullPointerException e) {
        }
    }

    public void checkIsArchived(IndividualDto individualDto) {
        log.info(String.format("checking if user archived %s", individualDto));
        if (individualDto.isArchived()) {
            throw new ArchivedClientException(String.format("Client with this ICP is archived, actual ICP is %s", individualDto.getActualIcp()));
        }
    }
}