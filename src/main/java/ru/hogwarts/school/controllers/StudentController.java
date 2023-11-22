package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.Collection;
import java.util.List;

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
    public ResponseEntity<Collection<Student>> getAllFaculty() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/age/{years}")
    public ResponseEntity<List<Student>> getStudentsByAge(@PathVariable Integer years) {
        List<Student> ageStudents = studentService.getAllStudents()
                .stream()
                .filter(student -> student.getAge() == years).toList();

        if (ageStudents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ageStudents);
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
}
