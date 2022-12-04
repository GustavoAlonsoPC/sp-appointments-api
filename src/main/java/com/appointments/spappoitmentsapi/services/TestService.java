package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.TestDTO;

import java.util.List;

public interface TestService {
    List<TestDTO> getAll();
    TestDTO post(TestDTO testDTO);
    TestDTO getByID(Long id);
    TestDTO put(TestDTO testDTO);
    Boolean delete(Long id);
}
