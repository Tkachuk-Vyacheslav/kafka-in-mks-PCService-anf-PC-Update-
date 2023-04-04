package org.client.service;

import org.client.common.dto.RFPassportDto;
import org.client.common.entity.RFPassport;

import java.util.Date;
import java.util.List;

public interface PassportService {

    void addPassport(RFPassportDto rfPassportDto);

    List<RFPassportDto> getPassporstByIcp(String icp);

    void editRfpassport(RFPassportDto dto, String uuid);

    void deletePassport(RFPassportDto dto, String uuid);

}
