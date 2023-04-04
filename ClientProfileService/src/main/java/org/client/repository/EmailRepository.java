package org.client.repository;

import org.client.common.entity.Contacts.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, String> {

}
