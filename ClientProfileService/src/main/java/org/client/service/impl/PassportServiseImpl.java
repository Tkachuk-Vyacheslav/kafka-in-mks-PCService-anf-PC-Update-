package org.client.service.impl;

import org.client.Exception.IncorrectRequestException;
import org.client.Exception.NotFoundException;
import org.client.common.dto.RFPassportDto;
import org.client.common.entity.Individual;
import org.client.common.entity.RFPassport;
import org.client.repository.IndividualRepository;
import org.client.repository.PassportRepository;
import org.client.service.IndividualService;
import org.client.service.PassportService;
import org.client.util.IndividualUtils;
import org.client.util.PassportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

public class PassportServiseImpl implements PassportService {

    @Autowired
    private IndividualUtils individualUtils;
    @Autowired
    private PassportUtils passportUtils;
    @Autowired
    private IndividualRepository individualRepository;
    @Autowired
    private PassportRepository passportRepository;

    private final IndividualService individualService;

    public PassportServiseImpl(IndividualService individualService) {
        this.individualService = individualService;
    }

    @Transactional
    @Override
    public void addPassport(RFPassportDto rfPassportDto) {
        RFPassport rfPassport = passportUtils.convertToEntity(rfPassportDto);
        passportRepository.save(rfPassport);
    }

    @Override
    public List<RFPassportDto> getPassporstByIcp(String icp) {
        Individual individual = individualRepository.findIndividualByIcp(icp).orElse(new Individual()); // находим  клиента

        //находим лист паспортов клиента
        List<RFPassport> rfPassportList =  passportRepository.findRFPassportByIndividualUuid(individual.getUuid()).orElse(new ArrayList<>());

        List<RFPassportDto> rfPassportDtoList = new ArrayList<>();

        // берем паспорт из листа rfPassportList, преобразуем в дто и помещаем в дтолист
        for (RFPassport passp : rfPassportList) {
            rfPassportDtoList.add(passportUtils.convertToDto(passp));
        }
        return rfPassportDtoList;
    }

    @Transactional
    @Override
    public void editRfpassport(RFPassportDto dto, String paspUuid) {
        // находим паспорт клиента по Uuid паспорта
        RFPassport rfPassport = passportRepository.findRFPassportByUuid(paspUuid).orElse(new RFPassport());

        if(rfPassport.getUuid() == null)
            throw new NotFoundException("не найдено паспорта с таким uuid");

            //проверка, что не ошиблись с Uuid паспорта в параметрах или теле запроса
            if (dto.getUuid().equals(paspUuid)) {
                RFPassport rfPassport_ = passportUtils.convertToEntity(dto);
                passportRepository.save(rfPassport_);
            } else {
                throw new IncorrectRequestException("! в теле запроса или впараметрах указан неправильный uuid клиента!");
            }

    }

    @Transactional
    @Override //удалить пасспорт по uuid passp
    public void deletePassport(RFPassportDto dto, String paspUuid) {
        // находим паспорт клиента по Uuid паспорта
        RFPassport rfPassport = passportRepository.findRFPassportByUuid(paspUuid).orElse(new RFPassport());
        if(rfPassport.getUuid() == null)
            throw new NotFoundException("не найдено паспорта с таким uuid");

        //проверка, что не ошиблись с Uuid паспорта в параметрах или теле запроса
        if (dto.getUuid().equals(paspUuid)) {
            passportRepository.deleteById(paspUuid);
        } else {
            throw new IncorrectRequestException("! в теле запроса или впараметрах указан неправильный uuid клиента!");
        }

    }

}
