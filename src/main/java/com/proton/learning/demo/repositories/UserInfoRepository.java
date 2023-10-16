package com.proton.learning.demo.repositories;

import com.proton.learning.demo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUsername(String username);
}
