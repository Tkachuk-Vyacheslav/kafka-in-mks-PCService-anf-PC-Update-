package org.client.repository;

import org.client.common.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, String>{

    Optional<Address> findByUuid(String uuid);
    Optional<Address> findByZipCode(String zipcode);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( // вставляем новый адресUUID, addressName, country, notFormAddrName в табл address
            value = "insert into public.address values " +
                    "(:uuid, :addressName, :country, :notFormAddrName, :zipCode)",
            nativeQuery = true)//
    void insertAddrToTableAddr(@Param("uuid") String uuid, @Param("addressName") String addressName, @Param("country") String country,
                               @Param("notFormAddrName") String notFormAddrName, @Param("zipCode") String zipCode);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( // вставляем  адресUuid и individualUuid в табл individ_address
            value = "insert into public.individ_address values " +
                    "(:individualUuid, :address_uuid)",
            nativeQuery = true)//
    void insertDataToTableIndivid_address(@Param("individualUuid") String individualUuid ,@Param("address_uuid") String address_uuid);

    //  ищем пользователя по адресу   (джоин из 2 таблиц через jpql)
    @Query("select indiv from Individual indiv join fetch indiv.addresses as addr where addr.zipCode = :zipCode")
    List<Individual> findByAddress(@Param("zipCode") String zipCode);

    //  ищем адрес клиента по icp клиента
    @Query("select addr from Address  addr join fetch addr.individuals as indiv where indiv.icp = :icp")
    List<Address> findByIcp(@Param("icp") String icp);

}
