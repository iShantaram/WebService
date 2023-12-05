package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    //private final HashMap<Long, Faculty> faculties = new HashMap<>();

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

    public List<Faculty> getByColor(String color) {
        return facultyRepository.findAll().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
