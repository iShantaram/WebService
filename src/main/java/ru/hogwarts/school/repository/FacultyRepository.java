package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByNameIgnoreCase(String name);
    Collection<Faculty> findByColorIgnoreCase(String color);

    Collection<? extends Faculty> findByNameContainsIgnoreCase(String name);

    Collection<? extends Faculty> findByColorContainsIgnoreCase(String color);
}
