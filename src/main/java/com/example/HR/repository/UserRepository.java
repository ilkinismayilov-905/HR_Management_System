package com.example.HR.repository;

import com.example.HR.entity.User;
import com.example.HR.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findByUsername(String username);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByPassword(String password);
    public List<User> findByRoles(UserRoles roles);
}
