package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Student get(long id) {
        return studentRepository.findById(id).get();
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }


    public Student editStudent(Student Student) {
        Student studentForUpdate = get(Student.getId());
        studentForUpdate.setName(Student.getName());
        studentForUpdate.setAge(Student.getAge());
        return studentForUpdate;
    }

    public Student deleteStudent(long id) {
        Student studentForDelete = get(id);
        studentRepository.delete(studentForDelete);
        return studentForDelete;
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Collection<Student> getStudentsByFaculty(long id) {
        return studentRepository.findByFaculty_id(id);
    }
}
