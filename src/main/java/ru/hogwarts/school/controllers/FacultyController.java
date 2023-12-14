package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculty(@RequestParam(required = false) String nameOrColor) {
        if (nameOrColor != null && !nameOrColor.isBlank()) {
            return ResponseEntity.ok(facultyService.findByNameOrColor(nameOrColor));
        }
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @GetMapping("/{color}")
    public ResponseEntity<Collection<Faculty>> getByColor(@PathVariable String color) {
        Collection<Faculty> facultiesByColor = facultyService.getByColor(color);
        if (facultiesByColor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultiesByColor);
    }

    @GetMapping("/studentsFromFaculty/{id}")
    public ResponseEntity<Collection<Student>> getStudents(@PathVariable Long id) {
        Faculty faculty = facultyService.get(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        Collection<Student> studentsByFaculty = faculty.getStudents();
        if (studentsByFaculty.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentsByFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty findFaculty = facultyService.editFaculty(faculty);
        if (findFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(findFaculty);
    }

    @DeleteMapping("{id}")
    public Faculty deleteFaculty(@PathVariable Long id) {
        return facultyService.deleteFaculty(id);
    }
}
