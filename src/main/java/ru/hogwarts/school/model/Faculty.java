package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ElementCollection(targetClass=String.class)
    private final Set<String> color = new HashSet<>();

    @OneToMany(mappedBy = "faculty")
    @JsonIgnore
    private Collection<Student> students;
    public Faculty() {}

    public Faculty(String name, Set<String> color) {
        this.name = name;
        this.color.addAll(color);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id && Objects.equals(name, faculty.name) && Objects.equals(color, faculty.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getColor() {
        return color;
    }

    public void setColor(Set<String> color) {
        this.color.clear();
        this.color.addAll(color);
    }

    public Collection<Student> getStudents() {
        return students;
    }
}
