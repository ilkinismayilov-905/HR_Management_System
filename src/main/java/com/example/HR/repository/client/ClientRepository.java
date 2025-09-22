package com.example.HR.repository.client;

import com.example.HR.entity.client.Client;
import com.example.HR.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByClientId(String clientId);
    List<Client> findByCompanyName(String companyName);
    List<Client> findByStatus(ClientStatus status);
}
