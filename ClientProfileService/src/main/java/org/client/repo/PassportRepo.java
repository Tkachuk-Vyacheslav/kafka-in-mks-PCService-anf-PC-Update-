package org.client.repo;


import org.client.common.entity.Individual;
import org.client.common.entity.RFPassport;
import org.client.common.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PassportRepo extends JpaRepository<RFPassport, String> {

    // найти псапорт по uuid клиента
    Optional<List<RFPassport>> findRFPassportByIndividualUuid (String uuid);



//    //  ищем все поля паспорта по  uuid клиента (jpql)
//    @Query(value = "from RFPassport as rfp join fetch rfp.individual as individ where individ.uuid = :uuid")
//    List<RFPassport> findAllFieldsByClientUuid(@Param("uuid") String uuid);

    //  ищем  паспорт по  uuid паспорта
    Optional<RFPassport> findRFPassportByUuid (String passpUuid);

    //  удаляем паспорт по uuid паспорта
    @Transactional
    void deleteRFPassportByUuid(String uuid);
}
