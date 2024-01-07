package ru.hogwarts.school.dto;

import java.util.Objects;

public class StudentDto {

    private String name;

    private int age;

    public StudentDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public StudentDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentDto that)) return false;
        return getAge() == that.getAge() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge());
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
