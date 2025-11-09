package com.example.HR.repository.user;

import com.example.HR.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findByFullname(String fullname);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByPassword(String password);
    boolean existsByEmail(String email);
//    public List<User> findByRoles(UserRoles roles);
}
