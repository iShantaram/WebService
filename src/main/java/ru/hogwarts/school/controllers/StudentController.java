package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
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
    public Student createStudent(@RequestBody Student Student) {
        return studentService.createStudent(Student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student Student = studentService.findStudent(id);
        if (Student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/age-between")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(@RequestParam Integer min, @RequestParam Integer max) {
        Collection<Student> ageBetweenStudents = studentService.findByAgeBetween(min, max);

        if (ageBetweenStudents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ageBetweenStudents);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student Student) {
        Student findStudent = studentService.editStudent(Student);
        if (findStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(findStudent);
    }

    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/get-faculty-by-student-id")
    public Faculty getFaculty(@RequestParam long id) {
        return studentService.getFaculty(id);
    }
}
