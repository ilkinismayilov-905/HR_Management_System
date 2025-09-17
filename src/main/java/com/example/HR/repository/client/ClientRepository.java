package com.example.HR.repository.client;

import com.example.HR.dto.client.ClientRequestDTO;
import com.example.HR.entity.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    ClientRequestDTO findByFullname(String fullname);
}
