package ru.hogwarts.school.controllers;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.StudentService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@WebMvcTest(StudentController.class)
class StudentControllerWebMvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    @Test
    void create() throws Exception {
        //Подготовка входных данных
        String name = "Garry Potter";
        int age = 18;
        Student studentForCreate = new Student(name, age);

        String request = objectMapper.writeValueAsString(studentForCreate);

        //Подготовка ожидаемого результата
        long id = 1L;
        Student studentAfterCreate = new Student(name, age);
        studentAfterCreate.setId(id);

        when(studentService.add(name, age)).thenReturn(studentAfterCreate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student") //send
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentForCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentForCreate.getAge()))
                .andReturn();
    }

    @Test
    void get() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Garry Potter";
        int age = 18;
        long id = 1L;
        Student studentForCreate = new Student(name, age);
        studentForCreate.setId(id);

        when(studentService.get(id)).thenReturn(studentForCreate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student") //send
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentForCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentForCreate.getAge()))
                .andReturn();
    }

    @Test
    void update() throws Exception {
        //Подготовка входных данных
        String name = "Garry Potter";
        int age = 18;
        long id = 2L;
        Student studentForUpdate = new Student(name, age);
        studentForUpdate.setId(id);

        String request = objectMapper.writeValueAsString(studentForUpdate);

        //Подготовка ожидаемого результата
        when(studentService.update(id, name, age)).thenReturn(studentForUpdate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student") //send
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentForUpdate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentForUpdate.getAge()))
                .andReturn();
    }

    @Test
    void delete() throws Exception {
        //Подготовка входных данных
        String name = "Garry Potter";
        int age = 18;
        long id = 1L;
        Student studentForDelete = new Student(name, age);
        studentForDelete.setId(id);

        //Подготовка ожидаемого результата
        when(studentService.delete(id)).thenReturn(studentForDelete);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student") //send
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentForDelete.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentForDelete.getAge()))
                .andReturn();
    }

    @Test
    void getWhenAgeBetween() throws Exception {
        //Подготовка входных данных
        String name1 = "Garry Potter";
        int age1 = 18;
        long id1 = 1L;
        Student studentForCreate1 = new Student(name1, age1);
        studentForCreate1.setId(id1);

        String name2 = "Germiona Grainger";
        int age2 = 17;
        long id2 = 2L;
        Student studentForCreate2 = new Student(name2, age2);
        studentForCreate2.setId(id2);

        //Подготовка ожидаемого результата
        List<Student> expectedStudent = Collections.singletonList(studentForCreate2);
        when(studentService.getWhenAgeBetween(anyInt(), anyInt())).thenReturn(expectedStudent);
        String expectedJson = "[{\"id\":2,\"name\":\"Germiona Grainger\",\"age\":17,\"faculty\":null}]";

        //Начало теста
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age-between") //send
                        .param("min", String.valueOf(1))
                        .param("max", String.valueOf(17)))
                .andExpect(status().isOk())
                .andReturn();
        String actualJson = result.getResponse().getContentAsString();
        assertEquals(expectedJson, actualJson);
    }

}