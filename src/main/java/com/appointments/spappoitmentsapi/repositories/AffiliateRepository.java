package com.appointments.spappoitmentsapi.repositories;

import com.appointments.spappoitmentsapi.entities.Affiliate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliateRepository extends JpaRepository<Affiliate, Long> {
}