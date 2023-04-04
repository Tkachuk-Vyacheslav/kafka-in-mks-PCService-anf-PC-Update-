package org.client.service.impl;

import lombok.AllArgsConstructor;
import org.client.common.dto.AddressDto;
import org.client.common.dto.IndividualDto;
import org.client.common.entity.*;
import org.client.repository.AddressRepository;
import org.client.repository.IndividualRepository;
import org.client.service.AddressService;
import org.client.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final IndividualService individualService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    IndividualRepository individualRepository;

    public AddressServiceImpl(IndividualService individualService) {
        this.individualService = individualService;
    }

    @Override //Создание нового адреса и его привязка к пользователю по icp пользователя
    public void addAddressForClient(String individualIcp, String addressName, String notFormAddrName, String country, String zipCode) {

        Individual individual = individualRepository.findIndividualByIcp(individualIcp).orElse(new Individual());

        String adrUuid = UUID.randomUUID().toString();

        addressRepository.insertAddrToTableAddr(adrUuid, addressName, country, notFormAddrName, zipCode);
        addressRepository.insertDataToTableIndivid_address(individual.getUuid() ,adrUuid);
    }

//    @Override
//    public AddressDto getAddressClient(String icp) {
//        return (AddressDto) Collections.singleton(individualService.getClient(icp).getAddress());
//    }

    @Override //найти лист клиентов по адресу
    public List<IndividualDto> getClientByAddress(String zipCode) {
        List<Individual> individualList = addressRepository.findByAddress(zipCode);
        List<IndividualDto> IndividualDtoList = new ArrayList<>();

        //для каждого элемента individualList создадим объект типа IndividualDto, и присвоим ему значения из элемента individualList.
        // Потом  - поместим этот объект в лист IndividualDtoList
//        for(Individual i: individualList){
//            IndividualDto individualDto = IndividualDto.builder().uuid(i.getUuid()).icp(i.getIcp()).name(i.getName()).
//                    fullName(i.getFullName()).surname(i.getSurname()).patronymic(i.getPatronymic()).gender(i.getGender()).
//                    placeOfBirth(i.getPlaceOfBirth()).countryOfBirth(i.getCountryOfBirth()).birthDate(i.getBirthDate()).documentsUuid(i.getDocuments().getUuid()).
//                    rfPassportUuid(i.getPassport().getUuid()).contactsUuid(i.getContacts().getUuid()).build();
//
//            IndividualDtoList.add(individualDto);
//        }
        return IndividualDtoList;
    }

    @Override //найти лист адресов клиента по icp клиента
    public List<AddressDto> getAddressByClienticp(String icp) {
        List<Address> address_list = addressRepository.findByIcp(icp);
        List<AddressDto> AddressDtoList = new ArrayList<>();

        //для каждого элемента address_list создадим объект типа AddressDto, и присвоим ему значения из элемента address_list.
        // Потом  - поместим этот объект в лист AddressDto
        for(Address a: address_list){
            AddressDto addressDto = AddressDto.builder().individualIcp(icp).uuid(a.getUuid()).notFormAddrName(a.getNotFormAddrName()).addressName(a.getAddressName()).
            country(a.getCountry()).zipCode(a.getZipCode()).build();
            AddressDtoList.add(addressDto);
        }
        return AddressDtoList;
    }

    @Transactional
    @Override  // редактировать address.
    public void editAddress(String uuid, String notFormAddrName, String addressName, String country, String zipCode) {
        Address editaddress = Address.builder().uuid(uuid).notFormAddrName(notFormAddrName).addressName(addressName).
                country(country).zipCode(zipCode).build();
        addressRepository.save(editaddress);
    }

    @Override //удалить адрес по зипкод
    public void deleteAddress(String zipcode) {
        Address address = addressRepository.findByZipCode(zipcode).orElse(new Address());
        addressRepository.deleteById(address.getUuid());
    }

}
