package ru.hogwarts.school.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createFaculty_success() {
        //Подготовка входных данных
        Faculty facultyForCreate = new Faculty("Gryffindor", "red");

        //Подготовка ожидаемого результата
        Faculty expectedFaculty = new Faculty("Gryffindor", "red");
        //expectedFaculty.setId(1L);

        //Начало теста
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyForCreate, Faculty.class);
        assertNotNull(postedFaculty);
        //assertEquals(expectedFaculty, postedFaculty);
    }

    @Test
    void getFaculty() {
        //Подготовка входных данных

        //Подготовка ожидаемого результата

        //Начало теста
    }

    @Test
    void getAllFaculty() {
        //Подготовка входных данных

        //Подготовка ожидаемого результата

        //Начало теста
    }

    @Test
    void getByColor() {
        //Подготовка входных данных

        //Подготовка ожидаемого результата

        //Начало теста
    }

    @Test
    void getStudents() {
        //Подготовка входных данных

        //Подготовка ожидаемого результата

        //Начало теста
    }

    @Test
    void editFaculty() {
        //Подготовка входных данных

        //Подготовка ожидаемого результата

        //Начало теста
    }

    @Test
    void deleteFaculty() {
        //Подготовка входных данных

        //Подготовка ожидаемого результата

        //Начало теста
    }
}