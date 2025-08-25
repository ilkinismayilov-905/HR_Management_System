package com.example.HR.repository;

import com.example.HR.entity.employee.EducationInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationInfoRepository extends JpaRepository<EducationInformation,Long> {

}
