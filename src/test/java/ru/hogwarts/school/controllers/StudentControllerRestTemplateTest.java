package ru.hogwarts.school.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

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

        // Тест не проходит, падает с ошибками
        // Варианты 2 и 3 по сути одно и тоже.
        //
        // org.springframework.web.client.RestClientException: Error while extracting response for type [class [Ljava.lang.Object;] and content type [application/json]
        // и
        // org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error:
        // Cannot deserialize value of type `[Ljava.lang.Object;` from Object value (token `JsonToken.START_OBJECT`)
        //
        // Также не понимаю, почему не работает вариант 1 по аналогии с Faculty/Student. Если я указал тип ответа Collection, разве он не должен возвращать коллекцию?
        // Или с Faculty/Student это работает, потому что Spring знает, как расшифровать Faculty и Student
        // благодаря аннотации Entity? А Json в Collection он не может?

        /*
         * Вариант 1
         */
        Collection<Student> students =
                restTemplate.getForObject("http://localhost:" + port + "/age-between?min=1&max=17", Collection.class);
        List<Student> studentsArrList = new ArrayList<>(students);
        assertNotNull(studentsArrList);
        assertEquals(studentsArrList.size(), 1);
        assertTrue(studentsArrList.contains(postedStudent2));

        /*
         * Вариант 2
         */
//        ResponseEntity<Student[]> responseEntityStudents =
//                restTemplate.getForEntity("http://localhost:" + port + "/age-between?min=18&max=30", Student[].class);
//        Student[] actualStudentsArr = responseEntityStudents.getBody();
//        assertNotNull(actualStudentsArr);
//        assertEquals(actualStudentsArr.length, 1);
//        assertSame(actualStudentsArr[0], postedStudent2);

        /*
         * Вариант 3
         */
//        ResponseEntity<Object[]> responseEntityObjects =
//                restTemplate.getForEntity("http://localhost:" + port + "/age-between?min=18&max=30", Object[].class);
//        Object[] objects = responseEntityObjects.getBody();
//        ObjectMapper mapper = new ObjectMapper();
//        List<Student> actualStudents = Arrays.stream(objects)
//                .map(object -> mapper.convertValue(object, Student.class))
//                .toList();
//        assertNotNull(actualStudents);
//        assertEquals(actualStudents.size(), 1);
//        assertTrue(actualStudents.contains(postedStudent1));

    }
}