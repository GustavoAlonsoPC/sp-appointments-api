package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.entities.Test;
import com.appointments.spappoitmentsapi.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

}
