package org.client.repo;

import org.client.common.entity.ContactMedium;
import org.client.common.entity.Contacts.Email;
import org.client.common.entity.Individual;
import org.client.common.entity.RFPassport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
public interface EmailRepo  extends JpaRepository<Email, String> {

    
}
