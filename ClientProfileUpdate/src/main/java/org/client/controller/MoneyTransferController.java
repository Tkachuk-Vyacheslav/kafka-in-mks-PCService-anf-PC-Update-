package org.client.controller;

import org.client.common.dto.MoneyTransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// это продюсер
@RestController
@RequestMapping("transfer")
public class MoneyTransferController {


    @Autowired
    private KafkaTemplate<Long, MoneyTransferDto> kafkaTemplate; // Для того, чтобы отправлять сообщения, нам потребуется объект KafkaTemplate<K, V>


    @PostMapping("/{icp}")
    public void sendMsg(@PathVariable(value="icp") Long icp, @RequestBody MoneyTransferDto msg){

        ListenableFuture<SendResult<Long, MoneyTransferDto>> future = kafkaTemplate.send("transfer", icp, msg);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }

}

