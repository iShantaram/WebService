package ru.hogwarts.school.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    @Autowired
    private final FacultyRepository facultyRepository;
    private final StudentService studentService;
    public FacultyService(FacultyRepository facultyRepository, StudentService studentService) {
        this.facultyRepository = facultyRepository;
        this.studentService = studentService;
    }
    public Faculty get(long id) {
        return facultyRepository.findById(id).get();
    }
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        Faculty facultyForUpdate = get(faculty.getId());
        facultyForUpdate.setName(faculty.getName());
        facultyForUpdate.setColor(faculty.getColor());
        return facultyRepository.save(facultyForUpdate);
    }

    public Faculty deleteFaculty(long id) {
        Faculty facultyForDelete = get(id);
        facultyRepository.deleteById(id);
        return facultyForDelete;
    }
    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }
    public Collection<Faculty> getByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }
    public Collection<Faculty> findByNameOrColor(String nameOrColor) {
        Set<Faculty> faculties = new HashSet<>();
        faculties.addAll(facultyRepository.findByNameContainsIgnoreCase(nameOrColor));
        faculties.addAll(facultyRepository.findByColorContainsIgnoreCase(nameOrColor));
        return faculties;
    }
    public Collection<Student> getStudents(long id) {
        return studentService.findByFacultyId(id);
    }
}
