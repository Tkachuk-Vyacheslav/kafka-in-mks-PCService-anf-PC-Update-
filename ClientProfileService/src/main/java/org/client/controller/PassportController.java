package org.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.client.common.dto.RFPassportDto;
import org.client.service.PassportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/passport")
public class PassportController {

    private final PassportService passportService;

    public PassportController(PassportService passportService){this.passportService = passportService;}

    // при создании паспорта, uuid паспорта система генерирует его автоматически! В дто uuid паспорта не задается
    @PostMapping("/create")
    @Operation(summary = "создaние паспорта клиента по uuid клиента")
    public ResponseEntity<Void> createPassport(@RequestBody RFPassportDto dto) {
//        try {
//            authUtil.checkAuth(request);
//        } catch (HttpClientErrorException e) {
//            return ResponseEntity.status(401).build();
//        }
        passportService.addPassport(dto);
        return ResponseEntity.status(201).build();
    }


    @GetMapping("/getPassportByClientIcp/{icp}")
    @Operation(summary = "Получить паспорта клиента по icp клиента")
    public ResponseEntity<List<RFPassportDto>> getPasspByClentIcp(
            @PathVariable(value="icp") String icp) {
//        try {
//            authUtil.checkAuth(request);
//        } catch (HttpClientErrorException e) {
//            return ResponseEntity.status(401).build();
//        }

        //individualService.checkIsArchived(individualDto);
        return new ResponseEntity<>(passportService.getPassporstByIcp(icp), HttpStatus.OK);
    }

    //установлено ограниение: в паспорте нельзя поменять uuid клиента!
    @PutMapping("/edit/{uuid}") //необходимо явно указать uuid паспорта в дто (в теле запроса) и в параметрах запроса
    @Operation(summary = "редактирование паспорта клиента")
    public ResponseEntity<Void> editPassp(@RequestBody RFPassportDto dto, @PathVariable(value="uuid") String uuid ,HttpServletRequest request) {
//        try {
//            authUtil.checkAuth(request);
//        } catch (HttpClientErrorException e) {
//            return ResponseEntity.status(401).build();
//        }
        passportService.editRfpassport(dto, uuid);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/delete/{uuid}")  // uuid passport нужен только в теле запроса и в параметрах
    @Operation(summary = "удаление паспорта по uuid паспорта")
    public ResponseEntity<Void> deletePassp(@RequestBody RFPassportDto dto, @PathVariable(value="uuid") String uuid, HttpServletRequest request) {
//        try {
//            authUtil.checkAuth(request);
//        } catch (HttpClientErrorException e) {
//            return ResponseEntity.status(401).build();
//        }
        passportService.deletePassport(dto, uuid);
        return ResponseEntity.status(200).build();
    }


}
