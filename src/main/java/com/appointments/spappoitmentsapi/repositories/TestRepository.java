package com.appointments.spappoitmentsapi.repositories;

import com.appointments.spappoitmentsapi.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}