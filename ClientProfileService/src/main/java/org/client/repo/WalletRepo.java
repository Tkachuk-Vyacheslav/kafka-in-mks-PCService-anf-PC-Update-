package org.client.repo;

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
public interface WalletRepo extends JpaRepository<Wallet, String>{

    Optional<Wallet> findWalletByUuid (String uuid);

    Optional<Wallet> findWalletByEuroWallet (String euroWallet);

    Optional<Wallet> findWalletByUsdWallet (String usdWallet);

    Optional<Wallet> findWalletByRubWallet (String rubWallet);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( // вставляем данные в табл Wallet
            value = "insert into public.wallet values " +
                    "(:wallet_uuid, :euro_wallet, :rub_wallet, :usd_wallet, :individ_uuid)",
            nativeQuery = true)//
    void insertToWalletTable(@Param("wallet_uuid") String wallet_uuid, @Param("euro_wallet") String euro_wallet, @Param("rub_wallet") String rub_wallet,
                               @Param("usd_wallet") String usd_wallet, @Param("individ_uuid") String individ_uuid);

    //  ищем  пользователя по wallet.uuid
    @Query("select indiv from Individual indiv join fetch indiv.wallets as w where w.uuid = :uuid")
    Individual findClientByWalletId(@Param("uuid") String uuid);

    //  ищем кошельки клиента по icp клиента
    @Query("select w from Wallet  w join fetch w.individual as indiv where indiv.icp = :icp")
    List<Wallet> findByIcp(@Param("icp") String icp);

}
