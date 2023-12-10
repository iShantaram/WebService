package ru.hogwarts.school.services;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
    public Faculty get(long id) {
        return facultyRepository.findById(id).get();
    }
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }
    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }
    public Faculty editFaculty(Faculty faculty) {
        Faculty facultyForUpdate = get(faculty.getId());
        facultyForUpdate.setName(faculty.getName());
        facultyForUpdate.setColor(faculty.getColor());
        return facultyForUpdate;
    }
    public Faculty deleteFaculty(long id) {
        Faculty facultyForDelete = get(id);
        facultyRepository.delete(facultyForDelete);
        return facultyForDelete;
    }
    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }
    public Collection<Faculty> getByColor(String color) {
        return facultyRepository.findAll().stream()
                .filter(faculty -> faculty.getColor().contains(color))
                .collect(Collectors.toList());
    }
    public Collection<Faculty> findByNameOrColor(String nameOrColor) {
        Set<Faculty> faculties = new HashSet<>();
        faculties.addAll(facultyRepository.findAll().stream()
                .filter(faculty -> faculty.getColor().contains(nameOrColor))
                .collect(Collectors.toSet()));
        faculties.addAll(facultyRepository.findAll().stream()
                .filter(faculty -> faculty.getName().equals(nameOrColor))
                .collect(Collectors.toSet()));
        faculties.addAll(facultyRepository.findByNameContainsIgnoreCase(nameOrColor));
        faculties.addAll(facultyRepository.findByColorContainsIgnoreCase(nameOrColor));
        return faculties;
    }

    public Faculty getFacultyByStudentId(long id) {
        for (Faculty faculty: facultyRepository.findAll()) {
            for (Student student: faculty.getStudents()) {
                if (student.getId() == id) {
                    return faculty;
                }
            }
        }
        return null;
    }


}
