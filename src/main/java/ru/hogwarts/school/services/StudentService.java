package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(String name, Integer age) {
        Student newStudent = new Student(name, age);
        newStudent = studentRepository.save(newStudent);
        return newStudent;
    }

    public Student get(long id) {
        return studentRepository.findById(id).get();
    }

    public Student update(long id, String name, Integer age) {
        Student studentForUpdate = get(id);
        studentForUpdate.setName(name);
        studentForUpdate.setAge(age);
        return studentRepository.save(studentForUpdate);
    }

    public Student delete(long id) {
        Student studentForDelete = get(id);
        studentRepository.deleteById(id);
        return studentForDelete;
    }

    public Collection<Student> getWhenAgeBetween(Integer min, Integer max) {
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }
}