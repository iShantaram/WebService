package ru.hogwarts.school.controllers;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.services.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody StudentDto student) {
        return studentService.add(student.getName(), student.getAge());
    }

    @GetMapping
    public Student get(@RequestParam long id) {
        return studentService.get(id);
    }

    //@GetMapping("/getAll")
    public Collection<Student> getAll() {
        return studentService.getAll();
    }



    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.update(student.getId(), student.getName(), student.getAge());
    }

    @DeleteMapping
    public Student delete(@RequestParam long id) {
        return studentService.delete(id);
    }

    @GetMapping("/age-between")
    public Collection<Student> getWhenAgeBetween(@RequestParam Integer min, @RequestParam Integer max) {
        return studentService.getWhenAgeBetween(min, max);
    }
}