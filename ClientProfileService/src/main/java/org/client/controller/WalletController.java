package org.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.client.common.dto.WalletDto;
import org.client.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService){this.walletService = walletService;}

    @PostMapping("/create/{icp}")
    @Operation(summary = "Создание нового кошелька и его привязка к пользователю по icp пользователя")
    public ResponseEntity<String> createAddress(@Valid @RequestBody WalletDto dto, @PathVariable(value="icp") String icp) throws Exception {
        walletService.addWalletForClient(dto.getEuroWallet(), dto.getRubWallet(), dto.getUsdWallet(),
                dto.getIndividualIcp(), icp);
        return ResponseEntity.ok("Wallet  was created successfully!");
    }

    @GetMapping("/getAll")
    @Operation(summary = "находим все кошельки")
    public ResponseEntity<List<WalletDto>> getAllWallets() {

        return new ResponseEntity<>(walletService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/getWalletByClientIcp/{icp}")
    @Operation(summary = "Информация о кошельке по icp клиента")
    public ResponseEntity<List<WalletDto>> getWalletByClientIcp(@Parameter(description = "icp") String Icp,
                                                              @PathVariable(value="icp") String icp) throws Exception {
        return new ResponseEntity<>(walletService.getWalletByIcp(icp), HttpStatus.OK);
    }

    @PutMapping("/edit/{uuid}")
    @Operation(summary = "редактирование кошелька по его uuid")
    public ResponseEntity<String> editWalletByUuid(@Valid @RequestBody WalletDto dto, @PathVariable(value="uuid") String uuidFromparam) throws Exception {

        walletService.editWallet(dto.getUuid(),dto.getIndividualUuid(), dto.getRubWallet(), dto.getEuroWallet(), dto.getUsdWallet(), uuidFromparam);
        return ResponseEntity.ok("wallet  was updated successfully!");
    }

    @PostMapping("/delete/{uuid}")  //post запрос с walletuuid в  теле
    @Operation(summary = "удаление кошелька по walletuuid")
    public ResponseEntity<String> deleteWalletByUuid(@RequestBody WalletDto dto, @PathVariable(value="uuid") String uuidFromparam) throws Exception {

        walletService.deleteWallet(dto.getUuid(), uuidFromparam);
        return ResponseEntity.ok("wallet  was deleted !");
    }

}
