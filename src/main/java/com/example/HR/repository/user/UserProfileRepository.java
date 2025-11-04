package com.example.HR.repository.user;

import com.example.HR.entity.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);
    @Query("SELECT p FROM UserProfile p WHERE p.user.id IN :userIds")
    List<UserProfile> findAllByUserIdIn(@Param("userIds") List<Long> userIds);

}
