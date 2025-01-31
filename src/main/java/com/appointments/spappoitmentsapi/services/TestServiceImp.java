package com.appointments.spappoitmentsapi.services;

import com.appointments.spappoitmentsapi.dto.TestDTO;
import com.appointments.spappoitmentsapi.entities.Test;
import com.appointments.spappoitmentsapi.repositories.TestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImp implements TestService {
    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TestServiceImp(TestRepository testRepository, ModelMapper modelMapper) {

        this.testRepository = testRepository;
        this.modelMapper = modelMapper;
    }

    public List<TestDTO> getAll() {
        List<Test> tests = testRepository.findAll();
        List<TestDTO> testDTOList = new ArrayList<>();
        for (Test t : tests) {
            TestDTO testDTO = modelMapper.map(t, TestDTO.class);
            testDTOList.add(testDTO);
        }
        return testDTOList;
    }

    public TestDTO post(TestDTO testDTO) {
        if (testDTO.getId() != null) {
            System.out.println("Trying to POST with an ID Field");
            return null;
        }

        if (testDTO.getName() == null || testDTO.getDescription() == null) {
            System.out.println("Missing requerided data");
            return null;
        }
        Test test = modelMapper.map(testDTO, Test.class);
        return modelMapper.map(testRepository.save(test), TestDTO.class);
    }

    public TestDTO getByID(Long id) {
        if (!testRepository.existsById(id)) {
            System.out.println("Do not exist");
            return null;
        }
        Test test = testRepository.findById(id).get();
        return modelMapper.map(test, TestDTO.class);
    }

    public TestDTO put(Long testId, TestDTO testDTO) {
        if (!testRepository.existsById(testId))return null;
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        Test test = testRepository.findById(testId).get();
        modelMapper.map(testDTO, test);
        testRepository.save(test);
        return modelMapper.map(test, TestDTO.class);
    }

    public Boolean delete(Long id) {
        if (testRepository.existsById(id)) {
            try {
                testRepository.deleteById(id);
            } catch (Exception e) {
                throw e;
            }
            return true;
        }
        return false;
    }
}
