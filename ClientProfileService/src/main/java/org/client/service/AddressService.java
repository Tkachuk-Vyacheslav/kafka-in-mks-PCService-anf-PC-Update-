package org.client.service;

import org.client.common.dto.AddressDto;
import org.client.common.dto.IndividualDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface AddressService {

    void addAddressForClient(String individualIcp, String addressName, String notFormAddrName, String country, String zipCode);

    List<IndividualDto> getClientByAddress(String zipCode);

    List<AddressDto> getAddressByClienticp(String icp);

    void editAddress(String uuid ,String notFormAddrName, String addressName, String country, String zipCode);

    void deleteAddress (String zipcode);
}
