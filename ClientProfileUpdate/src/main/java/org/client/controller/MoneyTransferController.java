package org.client.controller;

import org.client.common.dto.MoneyTransferDto;
import org.client.common.dto.TransferResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// это продюсер
@RequestMapping("transfer")
@RestController  // @RequestMapping("transfer")
public class MoneyTransferController {

    @Value("${kafka.reuest.topic}")
    private String requestTopic;

    @Autowired
    private ReplyingKafkaTemplate<String, MoneyTransferDto, TransferResultDto> replyingKafkaTemplate; // Для того, чтобы отправлять сообщения, нам потребуется объект KafkaTemplate<K, V>


//    @PostMapping("/{icp}")
//    public void sendMsg(@PathVariable(value = "icp") Long icp, @RequestBody MoneyTransferDto msg) {
//
//        ListenableFuture<SendResult<Long, MoneyTransferDto>> future = kafkaTemplate.send("transfer", icp, msg);
//        future.addCallback(System.out::println, System.err::println);
//        kafkaTemplate.flush();
//    }


    @PostMapping()
    public ResponseEntity<TransferResultDto> getObject( @RequestBody MoneyTransferDto moneyTransferDto)
            throws InterruptedException, ExecutionException {
        System.out.println(moneyTransferDto.getPhonenumber()+"  @@@@@");
        ProducerRecord<String, MoneyTransferDto> record = new ProducerRecord<>(requestTopic, null, moneyTransferDto.getIcp(), moneyTransferDto);
        RequestReplyFuture<String, MoneyTransferDto, TransferResultDto> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, TransferResultDto> response = future.get();
        return new ResponseEntity<>(response.value(), HttpStatus.OK);
    }


}