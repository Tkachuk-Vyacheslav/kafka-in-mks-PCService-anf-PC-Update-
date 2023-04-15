package org.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.client.Exception.NotEnoughMoneyException;
import org.client.common.dto.MoneyTransferDto;
import org.client.common.dto.TransferResultDto;
import org.client.service.WalletService;
import org.client.service.impl.WalletServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/**
 * ClientProfileService!
 *
 */
@SpringBootApplication
@EnableWebMvc
@Slf4j
@EnableKafka
public class MainApp {

    private final WalletService walletService;

    public MainApp(WalletService walletService) {
        this.walletService = walletService;
    }


    @KafkaListener(topics = "${kafka.reuest.topic}", groupId = "app.1")
    @SendTo
    public TransferResultDto handle(MoneyTransferDto moneyTransferDto) {

       String total =  walletService.moneyTransfer(moneyTransferDto, Long.parseLong(moneyTransferDto.getIcp()));

        TransferResultDto transferResultDto= new TransferResultDto();
        transferResultDto.setTransactionName("MoneyTransfer");

        if(total.equals("the transaction was successful")) {
            transferResultDto.setTransactionResult("the transaction was successful");
            transferResultDto.setDetails("all is ok!");
        } else if (total.equals(" не достаточно средств для перевода")) {
            transferResultDto.setTransactionResult(" the transaction failed");
            transferResultDto.setDetails("не достаточно средств для перевода");
        }

        return transferResultDto;
    }



//    @KafkaListener(topics="transfer")
//    public void orderListener(ConsumerRecord<Long, MoneyTransferDto> record){  // public void msgListener(String msg){ System.out.println(msg);}
//         System.out.println(record.key());
//        MoneyTransferDto mnt = record.value();
//        walletService.moneyTransfer(record.value(), record.key());
//
//    }

    public static void main(String[] args) {
        log.debug("starting ClientProfileService");
        SpringApplication.run(MainApp.class, args);
    }
}
