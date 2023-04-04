package org.client.service;

import org.client.common.dto.AddressDto;
import org.client.common.dto.IndividualDto;
import org.client.common.dto.WalletDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface WalletService {

    void addWalletForClient(String euroWallet, String rubWallet, String usdWallet, String individualIcp, String icpFromParam) throws Exception;

    List<WalletDto> getAll();

    List<WalletDto> getWalletByIcp(String icp) throws Exception;

    void editWallet(String uuid, String individualUuid, String rubWallet, String euroWallet, String usdWallet, String uuidFromParam) throws Exception;

    void deleteWallet(String uuid, String uuidFromParam) throws Exception;
}
