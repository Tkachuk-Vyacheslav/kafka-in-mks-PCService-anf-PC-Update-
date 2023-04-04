package org.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.client.common.dto.AddressDto;
import org.client.common.dto.IndividualDto;
import org.client.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/create")
    @Operation(summary = "Создание нового адреса и его привязка к пользователю по icp пользователя")
    public void createAddress(@RequestBody AddressDto dto) {
        addressService.addAddressForClient(dto.getIndividualIcp(), dto.getAddressName(), dto.getNotFormAddrName(), dto.getCountry(),
                dto.getZipCode());
    }

    @GetMapping("/getUserByAddr/{zipCode}") //ИЩЕМ пользователей по zipCode address
    @Operation(summary = "Информация о клиенте по  zipCode address")
    public ResponseEntity<List<IndividualDto>> getByAddress(@Parameter(description = "zipCode адреса") String ZipCode,
                                                      @PathVariable(value="zipCode") String zipCode) {
        return new ResponseEntity<>(addressService.getClientByAddress(zipCode), HttpStatus.OK);
    }

    @GetMapping("/getAddrByIndivIcp/{icp}") //ИЩЕМ адрес(а) по icp user
    @Operation(summary = "Информация о адресе по icp individual")
    public ResponseEntity<List<AddressDto>> getAddresByIndIcp(@Parameter(description = "icp") String Icp,
                                                              @PathVariable(value="icp") String icp) {
        return new ResponseEntity<>(addressService.getAddressByClienticp(icp), HttpStatus.OK);
    }

    @PutMapping("/edit")
    @Operation(summary = "редактирование адреса по uuid адреса")
    public ResponseEntity<Void> editAddress(@RequestBody AddressDto dto) {

        addressService.editAddress(dto.getUuid() ,dto.getNotFormAddrName(), dto.getAddressName(), dto.getCountry(), dto.getZipCode());
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/delete")  //post запрос с zipcode адреса в  теле
    @Operation(summary = "удаление адреса по zipcode") //????? uuid!!!!!!
    public ResponseEntity<Void> deleteAddressByZip(@RequestBody AddressDto dto) {

        addressService.deleteAddress(dto.getZipCode());
        return ResponseEntity.status(200).build();
    }
}
