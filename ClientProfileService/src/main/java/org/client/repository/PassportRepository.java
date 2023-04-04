package org.client.repository;

import org.client.common.entity.RFPassport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PassportRepository extends JpaRepository<RFPassport, String> {

    // найти псапорт по uuid клиента
    Optional<List<RFPassport>> findRFPassportByIndividualUuid (String uuid);

    //  ищем  паспорт по  uuid паспорта
    Optional<RFPassport> findRFPassportByUuid (String passpUuid);

    //  удаляем паспорт по uuid паспорта
    @Transactional
    void deleteRFPassportByUuid(String uuid);
}
