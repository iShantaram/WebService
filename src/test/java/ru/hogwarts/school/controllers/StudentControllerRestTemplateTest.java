package ru.hogwarts.school.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void cleanBase() {
        for (Student s : studentController.getAll()) {
            studentController.delete(s.getId());
        }
    }

    @Test
    void createStudent_success() {
        //Подготовка входных данных
        Student studentForCreate = new Student("Garry Potter", 17);

        //Подготовка ожидаемого результата
        Student expectedStudent = new Student("Garry Potter", 17);

        //Начало теста
        Student actualStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate, Student.class);
        expectedStudent.setId(actualStudent.getId());
        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getStudent_success() {
        //Подготовка входных данных
        Student studentForCreate = new Student("Garry Potter", 17);

        //Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate, Student.class);
        long id = postedStudent.getId();

        //Начало теста
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/student?id=" + id, Student.class);
        assertNotNull(actualStudent);
        assertEquals(postedStudent, actualStudent);
    }

    @Test
    void editStudent_success() {
        //Подготовка входных данных
        Student studentForCreate = new Student("Garry Potter", 17);

        //Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate, Student.class);

        long id = postedStudent.getId();

        Student studentForUpdate = new Student("Garry Potteroff", 18);
        studentForUpdate.setId(id);

        //Начало теста
        this.restTemplate.put("http://localhost:" + port + "/student", studentForUpdate);
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/student?id=" + id, Student.class);
        assertNotNull(actualStudent);
        assertEquals(studentForUpdate, actualStudent);
    }

    @Test
    void deleteStudent_success() {
        //Подготовка входных данных
        Student studentForCreate = new Student("Garry Potter", 18);

        //Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate, Student.class);
        long id = postedStudent.getId();

        //Начало теста
        Student studentForDelete = this.restTemplate.getForObject("http://localhost:" + port + "/student?id=" + id, Student.class);
        assertNotNull(studentForDelete);
        assertEquals(postedStudent, studentForDelete);
        this.restTemplate.delete("http://localhost:" + port + "/student?id=" + id);
        Student studentAfterDelete = this.restTemplate.getForObject("http://localhost:" + port + "/student?id=" + id, Student.class);
        assertNull(studentAfterDelete.getId());
        assertNull(studentAfterDelete.getName());
    }

    @Test
    void getStudentsByAgeBetween_success() {
        //Подготовка входных данных


        Student studentForCreate1 = new Student("Garry Potter", 18);
        Student studentForCreate2 = new Student("Germiona Grainger", 17);

        //Подготовка ожидаемого результата
        Student postedStudent1 = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate1, Student.class);
        long id1 = postedStudent1.getId();

        Student postedStudent2 = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate2, Student.class);
        long id2 = postedStudent2.getId();

        //Начало теста
        ResponseEntity<List<Student>> responseEntity =
                restTemplate.exchange(
                        "http://localhost:" + port + "/student/age-between?min=1&max=17",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Student>>() {}
                );
        List<Student> students = responseEntity.getBody();

        assertNotNull(students);
        assertEquals(students.size(), 1);
        assertTrue(students.contains(postedStudent2));

    }
}