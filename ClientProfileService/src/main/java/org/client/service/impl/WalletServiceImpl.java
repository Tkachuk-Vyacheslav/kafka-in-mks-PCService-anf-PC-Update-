package org.client.service.impl;

import lombok.AllArgsConstructor;
import org.client.Exception.*;
import org.client.common.dto.WalletDto;
import org.client.common.entity.Individual;
import org.client.common.entity.Wallet;
import org.client.repo.IndividualRepo;
import org.client.repo.WalletRepo;
import org.client.service.IndividualService;
import org.client.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.*;

@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final IndividualService individualService;

    @Autowired
    WalletRepo walletRepo;

    @Autowired
    IndividualRepo individualRepo;

    public WalletServiceImpl(IndividualService individualService) {
        this.individualService = individualService;
    }

    @Override // создать новый кошелек и привязать его к пользователю по icp client
    public void addWalletForClient(String euroWallet, String rubWallet, String usdWallet, String individualIcp, String icpFromParam) throws Exception {

            if (!individualIcp.equals(icpFromParam)) {
                throw new IncorrectRequestException(" клиентские icp в теле запроса и в параметрах должны быть одинаковы");
            } else if (euroWallet == null || rubWallet == null || usdWallet == null || individualIcp == null || icpFromParam == null) {
                throw new IncorrectRequestException(" wallet, icp не могут быть null!");
            }

            Individual individual = individualRepo.findIndividualByIcp(individualIcp).orElse(new Individual()); // чтобы найти uuid клиента по его icp
            if (individual.getUuid() == null) {
                throw new NotFoundException("клиент с таким Icp не найден");
            }
            // если счета одинаковы
            if (euroWallet.equals(rubWallet) || rubWallet.equals(usdWallet) || euroWallet.equals(usdWallet)) {
                throw new RepeatDataException("счета клиента должны отличаться друг от друга");
            }

        //проверим, что счет состоит только из цифр
        boolean isNumeric = euroWallet.chars().allMatch( Character::isDigit );
        if (euroWallet.chars().allMatch( Character::isDigit ) == false || usdWallet.chars().allMatch( Character::isDigit ) == false||
            rubWallet.chars().allMatch( Character::isDigit ) == false)
            throw new IncorrectRequestException("счета в кошельке должны состоять только из цифр");


            // проверка, не используется ли какой-то ( euroWallet,  rubWallet,  usdWallet) счет в другом кошельке
            List<String> listWallet = new ArrayList<>();
            Collections.addAll(listWallet, euroWallet, usdWallet, rubWallet);
            for (String s : listWallet) { // по каждому счету (рубль, долар, евро) пытаемся найти кошелек. Если получается - значит этот счет уже занят
                if (walletRepo.findWalletByEuroWallet(s).orElse(new Wallet()).getUuid() != null ||
                        walletRepo.findWalletByUsdWallet(s).orElse(new Wallet()).getUuid() != null ||
                        walletRepo.findWalletByRubWallet(s).orElse(new Wallet()).getUuid() != null) {
                    throw new RepeatDataException(s + "  уже существует в каком-то другом кошельке");
                }
            }

        try {
            //вставляем данные в табл кошельков с помощью запроса к бд
            walletRepo.insertToWalletTable(UUID.randomUUID().toString(), euroWallet, rubWallet, usdWallet, individual.getUuid());
        } catch (Exception e) {
            //throw new IncorrectRequestException("проблемы с подключением к БД");
            e.printStackTrace();
        }

    }

    @Override //получить все кошельки
    public List<WalletDto> getAll() {
        try {
            List<Wallet> walletList = walletRepo.findAll();
            List<WalletDto> walletDtoList = new ArrayList<>();

            //для каждого элемента walletList создадим объект типа WalletDto, и присвоим ему значения из элемента walletList.
            // Потом  - поместим этот объект в лист walletDtoList
            for (Wallet w : walletList) {
                WalletDto walletDto = WalletDto.builder().uuid(w.getUuid()).individualIcp(walletRepo.findClientByWalletId(w.getUuid()).getIcp()).
                        rubWallet(w.getRubWallet()).euroWallet(w.getEuroWallet()).usdWallet(w.getUsdWallet()).individualUuid(w.getIndividual().getUuid()).build();
                walletDtoList.add(walletDto);
            }
            return walletDtoList;
        } catch (NullPointerException e) {
            throw new NotFoundException("Не найдено ни одного wallet");
        }
    }

    @Override //найти кошельки клиента по его icp
    public List<WalletDto> getWalletByIcp(String icp) throws Exception {
        try {
            if (icp == null) {
                throw new EmptyFieldException(" icp не может быть null");
            } else if (individualRepo.findIndividualByIcp(icp).orElse(new Individual()).getUuid() == null) {
                throw new NotFoundException("Клиента с таким  icp не существует");
            }
            // находим лист кошельков
            List<Wallet> walletList = walletRepo.findByIcp(icp);
            List<WalletDto> walletDtoList = new ArrayList<>();

            //для каждого элемента walletList создадим объект типа WalletDto, и присвоим ему значения из элемента walletList.
            // Потом  - поместим этот объект в лист walletDtoList
            for (Wallet w : walletList) {
                WalletDto walletDto = WalletDto.builder().uuid(w.getUuid()).individualIcp(walletRepo.findClientByWalletId(w.getUuid()).getIcp()).
                        rubWallet(w.getRubWallet()).euroWallet(w.getEuroWallet()).usdWallet(w.getUsdWallet()).individualUuid(w.getIndividual().getUuid()).build();
                walletDtoList.add(walletDto);
            }
            return walletDtoList;
        } catch(NotFoundException e) {
            throw new NotFoundException("Клиента с таким  icp не существует");
        }
    }

    @Transactional
    @Override  // редактировать wallet.
    public void editWallet(String uuid,String individualUuid, String rubWallet, String euroWallet, String usdWallet, String uuidFromParam) throws Exception {
        //нашли клиента по айди кошелька
        Individual individualByWallet = individualRepo.findCleintByWalletUuid(uuidFromParam);
        Individual indByIndividualUuid = individualRepo.findIndividualByUuid(individualUuid).orElse(new Individual());

        if(!individualByWallet.getUuid().equals(indByIndividualUuid.getUuid()))
            throw new IncorrectRequestException("individualUuid  подменен?");

        if(!uuid.equals(uuidFromParam)) {
            throw new IncorrectRequestException( "uuid в теле запроса и в параметрах должны быть одинаковы");
        } else if(walletRepo.findWalletByUuid(uuid).orElse(new Wallet()).getUuid() == null) {
            throw new NotFoundException("кошелька с таким uuid не найдено");
        } else if(individualRepo.findIndividualByUuid(individualUuid).orElse(new Individual()).getIcp() == null) {
            throw new NotFoundException("client с таким uuid не найден");
        } else if(euroWallet.equals(rubWallet) || rubWallet.equals(usdWallet) || euroWallet.equals(usdWallet)) {
            throw new RepeatDataException("счета клиента должны отличаться друг от друга");
        }

        try {
            // проверка, не используется ли какой-то ( euroWallet,  rubWallet,  usdWallet) счет в другом кошельке
            List<String> listWallet = new ArrayList<>();
            Collections.addAll(listWallet, euroWallet, usdWallet, rubWallet);
            for (String s : listWallet) {  // если кошелек , котор мы нашли по счетУ (евро, долар, рубль) не ноль и его uuid не равен uuid текущего кошелька
                if ((walletRepo.findWalletByEuroWallet(s).orElse(new Wallet()).getUuid() != null && !walletRepo.findWalletByEuroWallet(s).orElse(new Wallet()).getUuid().equals(uuid)) ||
                        (walletRepo.findWalletByUsdWallet(s).orElse(new Wallet()).getUuid() != null && !walletRepo.findWalletByUsdWallet(s).orElse(new Wallet()).getUuid().equals(uuid)) ||
                        (walletRepo.findWalletByRubWallet(s).orElse(new Wallet()).getUuid() != null && !walletRepo.findWalletByRubWallet(s).orElse(new Wallet()).getUuid().equals(uuid))) {
                    throw new RepeatDataException(s + " уже существует в каком-то другом кошельке");
                }
            }
            Wallet editWallet = Wallet.builder().uuid(uuid).rubWallet(rubWallet).euroWallet(euroWallet).usdWallet(usdWallet).build();
            editWallet.setIndividual(walletRepo.findClientByWalletId(uuid)); // добавляем отдельно, так как поле individual приватное
            walletRepo.save(editWallet);
        } catch(ConnectException e) {
            throw new ConnectException(" no connect to db");
        }
    }

    @Override //удалить кошелек по uuid
    public void deleteWallet(String uuid, String uuidFromParam) throws Exception {
    if(walletRepo.findWalletByUuid(uuid).orElse(new Wallet()).getUuid() == null) {
        throw new NotFoundException(" кошелек с таким uuid не найден ");
        }else if(uuid.equals(uuidFromParam)) {
            walletRepo.deleteById(uuid);
        } else {
            throw new IncorrectRequestException(" uuid  в теле запроса и параметре должны быть одинаковы");
        }
    }


}
