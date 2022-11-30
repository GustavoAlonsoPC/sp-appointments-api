package com.appointments.spappoitmentsapi.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
class TestRepositoryTest {

    @Autowired
    private TestRepository testRepository;

    @BeforeEach
    void setUp() {
        List<com.appointments.spappoitmentsapi.entities.Test> testList = new ArrayList<>();
        com.appointments.spappoitmentsapi.entities.Test test1
                = new com.appointments.spappoitmentsapi.entities.Test(null, "Test1", "Desc1", null);
        com.appointments.spappoitmentsapi.entities.Test test2
                = new com.appointments.spappoitmentsapi.entities.Test(null, "Test2", "Desc2", null);
        com.appointments.spappoitmentsapi.entities.Test test3
                = new com.appointments.spappoitmentsapi.entities.Test(null, "Test3", "Desc3", null);
        com.appointments.spappoitmentsapi.entities.Test test4
                = new com.appointments.spappoitmentsapi.entities.Test(null, "Test4", "Desc4", null);


        testList.add(test1);
        testList.add(test2);
        testList.add(test3);
        testList.add(test4);

        testRepository.saveAll(testList);
    }

    @Test
    public void getAllFromRepoTest() {
        List<com.appointments.spappoitmentsapi.entities.Test> testList
                = testRepository.findAll();

        assertThat(testList).isNotNull();
        assertThat(testList.size()).isEqualTo(4);
    }

    @Test
    public void getByNonExistingId() {
        assertThatThrownBy(()-> testRepository.findById(888L).get()).isInstanceOf(Exception.class);
    }

    @Test
    public void getByIdTest() {
        com.appointments.spappoitmentsapi.entities.Test test = testRepository.findById(5L).get();
        assertThat(test).isNotNull();
        assertThat(test.getName()).isEqualTo("Test1");
    }
}