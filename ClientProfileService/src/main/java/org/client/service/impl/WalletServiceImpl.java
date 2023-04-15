package org.client.service.impl;

import lombok.AllArgsConstructor;
import org.client.Exception.ConnectException;
import org.client.Exception.EmptyFieldException;
import org.client.Exception.IncorrectRequestException;
import org.client.Exception.NotEnoughMoneyException;
import org.client.Exception.NotFoundException;
import org.client.Exception.RepeatDataException;
import org.client.common.dto.MoneyTransferDto;
import org.client.common.dto.WalletDto;
import org.client.common.dto.Wallets.EuroWalletDto;
import org.client.common.dto.Wallets.RubWalletDto;
import org.client.common.dto.Wallets.UsdWalletDto;
import org.client.common.entity.Individual;
import org.client.common.entity.WalletMedium;
import org.client.common.entity.Wallets.EuroWallet;
import org.client.common.entity.Wallets.RubWallet;
import org.client.common.entity.Wallets.UsdWallet;
import org.client.repository.IndividualRepository;
import org.client.repository.Wallet.EuroWalletRepository;
import org.client.repository.Wallet.RubWalletRepository;
import org.client.repository.Wallet.UsdWalletRepository;
import org.client.repository.WalletRepository;
import org.client.service.IndividualService;
import org.client.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.client.common.util.Сurrency;

@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final IndividualService individualService;

    @Autowired
    WalletRepository walletRepository;
    @Autowired
    RubWalletRepository rubWalletRepository;
    @Autowired
    EuroWalletRepository euroWalletRepository;
    @Autowired
    UsdWalletRepository usdWalletRepository;

    @Autowired
    IndividualRepository individualRepository;

    public WalletServiceImpl(IndividualService individualService) {
        this.individualService = individualService;
    }

    @Transactional
    @Override  // редактировать wallet.
    public void editWallet(WalletDto dto, String icp) throws Exception {

        // получим кошельки клиента (руб, евро, доллар), переприсвоим им значения
        EuroWallet euroWallet = euroWalletRepository.findEuroWallByClientIcp(icp);
        euroWallet.setValue(dto.getEuroWalletDto().getValue());

        RubWallet rubWallet = rubWalletRepository.findRubWallByClientIcp(icp);
        rubWallet.setValue(dto.getRubWalletDto().getValue());

        UsdWallet usdWallet = usdWalletRepository.findUsdWallByClientIcp(icp);
        usdWallet.setValue(dto.getUsdWalletDto().getValue());

        // get them walletMedium
        WalletMedium walletMedium = walletRepository.findWalletMediumByClientIcp(icp);
        walletMedium.setEuroWallets(euroWallet);
        walletMedium.setUsdWallets(usdWallet);
        walletMedium.setRubWallets(rubWallet);

        walletRepository.save(walletMedium);
    }

    @Override
    public void editWalletRub(RubWalletDto dto, String icp) throws Exception {
        RubWallet rubWallet = rubWalletRepository.findRubWallByClientIcp(icp);
        rubWallet.setValue(dto.getValue());
        WalletMedium walletMedium = walletRepository.findWalletMediumByClientIcp(icp);
        walletMedium.setRubWallets(rubWallet);

        walletRepository.save(walletMedium);

    }

    @Override // создать новый кошелек и привязать его к пользователю по icp client
    public void addWalletForClient(WalletDto dto, String individualIcp, String icpFromParam) throws Exception {

        // создадим uuid кошельков
        String euroWallet = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)).substring(2, 28);
        String rubWallet = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)).substring(2, 28);
        String usdWallet = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)).substring(2, 28);

        String walletMediumUuid = UUID.randomUUID().toString().replaceAll("-", ""); //strips '-'

        if (!individualIcp.equals(icpFromParam)) {
            throw new IncorrectRequestException(" клиентские icp в теле запроса и в параметрах должны быть одинаковы");
        } else if (euroWallet == null || rubWallet == null || usdWallet == null || individualIcp == null || icpFromParam == null) {
            throw new IncorrectRequestException(" walletUuid, icp не могут быть null!");
        }

        Individual individual = individualRepository.findIndividualByIcp(individualIcp).orElse(new Individual()); // чтобы найти uuid клиента по его icp
        if (individual.getUuid() == null) {
            throw new NotFoundException("клиент с таким Icp не найден");
        }
        // если uuid кошельков одинаковы
        if (euroWallet.equals(rubWallet) || rubWallet.equals(usdWallet) || euroWallet.equals(usdWallet)) {
            throw new RepeatDataException("uuid кошельков должны отличаться друг от друга");
        }

        //проверим, что uuid кошельков (евро, руб, доллар) состоит только из цифр
        boolean isNumeric = euroWallet.chars().allMatch(Character::isDigit);
        if (euroWallet.chars().allMatch(Character::isDigit) == false || usdWallet.chars().allMatch(Character::isDigit) == false ||
                rubWallet.chars().allMatch(Character::isDigit) == false)
            throw new IncorrectRequestException("uuid кошельков должны состоять только из цифр");

        WalletMedium walletMedium = new WalletMedium(); // = walletRepository.findWalletByUuid(walletMediumUuid).orElse(new WalletMedium());

        // передадим нашему объекту WalletMedium клиента
        walletMedium.setIndividual(individual);
        walletMedium.setUuid(walletMediumUuid);
        walletRepository.save(walletMedium);

        // создадим объекты EuroWallet, RubWallet, UsdWallet
        EuroWallet euroW = EuroWallet.builder().uuid(euroWallet).value(dto.getEuroWalletDto().getValue()).walletMedium(walletMedium).build();
        walletMedium.setEuroWallets(euroW);
        euroWalletRepository.save(euroW);

        RubWallet rubW = RubWallet.builder().uuid(rubWallet).value(dto.getRubWalletDto().getValue()).walletMedium(walletMedium).build();
        walletMedium.setRubWallets(rubW);
        rubWalletRepository.save(rubW);

        UsdWallet usdW = UsdWallet.builder().uuid(usdWallet).value(dto.getUsdWalletDto().getValue()).walletMedium(walletMedium).build();
        walletMedium.setUsdWallets(usdW);
        usdWalletRepository.save(usdW);

        //теперь передадим клиенту кошельки
        individual.setWallets(walletMedium);
        individualRepository.save(individual);

    }

    @Override //найти кошельки клиента по его icp
    public List<Object> getWalletByIcp(String icp) throws Exception {

        if (icp == null) {
            throw new EmptyFieldException(" icp не может быть null");
        } else if (individualRepository.findIndividualByIcp(icp).orElse(new Individual()).getUuid() == null) {
            throw new NotFoundException("Клиентов с таким  icp не существует");
        }

        // получим кошельки клиента (руб, евро, доллар)
        EuroWallet euroWallet = euroWalletRepository.findEuroWallByClientIcp(icp);
        RubWallet rubWallet = rubWalletRepository.findRubWallByClientIcp(icp);
        UsdWallet usdWallet = usdWalletRepository.findUsdWallByClientIcp(icp);

        //преобразуем кажд кошелек в дто
        EuroWalletDto euroWalletDto = EuroWalletDto.builder().currency(Сurrency.EURO).uuid(euroWallet.getUuid()).value(euroWallet.getValue()).build();
        RubWalletDto rubWalletDto = RubWalletDto.builder().currency(Сurrency.RUB).uuid(rubWallet.getUuid()).value(rubWallet.getValue()).build();
        UsdWalletDto usdWalletDto = UsdWalletDto.builder().currency(Сurrency.USD).uuid(usdWallet.getUuid()).value(usdWallet.getValue()).build();

        List<Object> list = new ArrayList<>();

        WalletDto walletDto = new WalletDto();
        walletDto.setEuroWalletDto(euroWalletDto);
        walletDto.setRubWalletDto(rubWalletDto);
        walletDto.setUsdWalletDto(usdWalletDto);
        walletDto.setIndividualIcp(icp);

        list.add(walletDto);
        return list;

    }

    @Override //удалить кошелек по uuid
    public void deleteWallet(String uuid, String uuidFromParam) throws Exception {
        if (walletRepository.findWalletByUuid(uuid).orElse(new WalletMedium()).getUuid() == null) {
            throw new NotFoundException(" кошелек с таким uuid не найден ");
        } else if (uuid.equals(uuidFromParam)) {
            walletRepository.deleteById(uuid);
        } else {
            throw new IncorrectRequestException(" uuid  в теле запроса и параметре должны быть одинаковы");
        }
    }

    // перевод средств по номеру телефона
    @Override
    public String moneyTransfer(MoneyTransferDto dto, Long icpFromParam) {
        String success = "the transaction was successful";
        String failure = "the transaction failed";
        String notEnoughMoney = " не достаточно средств для перевода";

        if (icpFromParam != Long.parseLong(dto.getIcp()))
            throw new IncorrectRequestException(" icp  в теле запроса и параметре должны быть одинаковы");

        // find acceptor by phonenumber
        Individual clientAccepter = individualRepository.findByPhNumOptional(dto.getPhonenumber()).orElse(new Individual());
        if (clientAccepter.getUuid() == null)
            throw new IncorrectRequestException(" не найден получатель средств по такому тлф");

        // find sender by phonenumber
        Individual clientSender = individualRepository.findIndividualByIcp(dto.getIcp()).orElse(new Individual());
        if (clientAccepter.getUuid() == null)
            throw new IncorrectRequestException(" не найден отправитель средств по такому тлф");

        //проверим, есть ли у них кошельки
        WalletMedium walletMediumSender = walletRepository.findWalletMediumByClientIcpOptional(dto.getIcp()).orElse(new WalletMedium());
        if (walletMediumSender.getUuid() == null)
            throw new IncorrectRequestException(" не найден кошелек для этого отправителя средств ");

        WalletMedium walletMediumAccepter = walletRepository.findWalletMediumByClientIcpOptional(clientAccepter.getIcp()).orElse(new WalletMedium());
        if (walletMediumAccepter.getUuid() == null)
            throw new IncorrectRequestException(" не найден кошелек для этого получателя средств ");

        if (dto.getCurrency().toString().equals("EURO")) {

            //находим средства  на евро кошельке отправителя
            String euroValueSender = euroWalletRepository.findEuroWallByClientIcp(dto.getIcp()).getValue();

            // достаточно ли средств для перевода?
            if ((Double.parseDouble(euroValueSender) - Double.parseDouble(dto.getPayment())) >= 0) {
                Double valueMinusPayment = Double.parseDouble(euroValueSender) - Double.parseDouble(dto.getPayment()); // найдем разницу

                EuroWallet senderEuroWallet = euroWalletRepository.findEuroWallByClientIcp(dto.getIcp()); // find eurowallet of sender
                senderEuroWallet.setValue(Double.toString(valueMinusPayment));   // перезапишем уменьшенную после перевода сумму еврокошельку отправителя
                euroWalletRepository.save(senderEuroWallet);  // сохраним-обновим еврокошелек отправителя

                // найдем еврокошелек аксептера
                EuroWallet accepterEuroWallet = euroWalletRepository.findEuroWallByClientIcp(clientAccepter.getIcp());
                Double acceptEuroWalletValuePlusPayment = Double.parseDouble(accepterEuroWallet.getValue()) + Double.parseDouble(dto.getPayment()); // прибавим payment
                accepterEuroWallet.setValue(Double.toString(acceptEuroWalletValuePlusPayment)); // send to accepterEuroWallet new value
                euroWalletRepository.save(accepterEuroWallet);
                return success;
            } else {
                return notEnoughMoney;
            }

        } else if (dto.getCurrency().toString().equals("RUB")) {   // if currency rub
            //находим средства  на rub кошельке отправителя
            String rubValueSender = rubWalletRepository.findRubWallByClientIcp(dto.getIcp()).getValue();
            System.out.println(" @@@@ rub");
            // достаточно ли средств для перевода?
            if ((Double.parseDouble(rubValueSender) - Double.parseDouble(dto.getPayment())) >= 0) {
                Double valueMinusPayment = Double.parseDouble(rubValueSender) - Double.parseDouble(dto.getPayment()); // найдем разницу

                RubWallet senderRubWallet = rubWalletRepository.findRubWallByClientIcp(dto.getIcp()); // find rubwallet of sender
                senderRubWallet.setValue(Double.toString(valueMinusPayment));   // перезапишем уменьшенную после перевода сумму rubкошельку отправителя
                rubWalletRepository.save(senderRubWallet);  // сохраним-обновим rubкошелек отправителя

                // найдем rubкошелек аксептера
                RubWallet accepterRubWallet = rubWalletRepository.findRubWallByClientIcp(clientAccepter.getIcp());
                Double acceptRubWalletValuePlusPayment = Double.parseDouble(accepterRubWallet.getValue()) + Double.parseDouble(dto.getPayment()); // прибавим payment
                accepterRubWallet.setValue(Double.toString(acceptRubWalletValuePlusPayment)); // send to accepterEuroWallet new value
                rubWalletRepository.save(accepterRubWallet);
                return success;
            }else {
                return notEnoughMoney;
            }

        }else if (dto.getCurrency().toString().equals("USD")) { // if currency USD
                    //находим средства  на usd кошельке отправителя
                    String usdValueSender = usdWalletRepository.findUsdWallByClientIcp(dto.getIcp()).getValue();

                    // достаточно ли средств для перевода?
                    if ((Double.parseDouble(usdValueSender) - Double.parseDouble(dto.getPayment())) >= 0) {
                        Double valueMinusPayment = Double.parseDouble(usdValueSender) - Double.parseDouble(dto.getPayment()); // найдем разницу

                        UsdWallet senderUsdWallet = usdWalletRepository.findUsdWallByClientIcp(dto.getIcp()); // find wallet of sender
                        senderUsdWallet.setValue(Double.toString(valueMinusPayment));   // перезапишем уменьшенную после перевода сумму rubкошельку отправителя
                        usdWalletRepository.save(senderUsdWallet);  // сохраним-обновим кошелек отправителя

                        // найдем кошелек аксептера
                        UsdWallet accepterUsdWallet = usdWalletRepository.findUsdWallByClientIcp(clientAccepter.getIcp());
                        Double acceptUsdWalletValuePlusPayment = Double.parseDouble(accepterUsdWallet.getValue()) + Double.parseDouble(dto.getPayment()); // прибавим payment
                        accepterUsdWallet.setValue(Double.toString(acceptUsdWalletValuePlusPayment)); // send to accepterWallet new value
                        usdWalletRepository.save(accepterUsdWallet);
                        return success;
                    } else {
                        return notEnoughMoney;
                    }
                }
                return failure;
            }





    }





