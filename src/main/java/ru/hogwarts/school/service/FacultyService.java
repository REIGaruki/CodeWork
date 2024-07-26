package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long idCounter = 0L;
    public Faculty createFaculty(String name, String color) {
        Faculty faculty = new Faculty(++idCounter, name, color);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }
    public List<Faculty> getAllSFaculties() {
        return faculties.values().stream().toList();
    }
    public Faculty readFaculty(Long id) {
        if (!faculties.containsKey(id)) {
            throw new RuntimeException();
        }
        return faculties.get(id);
    }
    public Faculty updateFaculty(Long id, String name, String color) {
        if (!faculties.containsKey(id)) {
            throw new RuntimeException();
        }
        faculties.get(id).setName(name);
        faculties.get(id).setColor(color);
        return faculties.get(id);
    }
    public Faculty deleteFaculty(Long id) {
        if (!faculties.containsKey(id)) {
            throw new RuntimeException();
        }
        Faculty faculty = faculties.get(id);
        faculties.remove(id);
        return faculty;
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return faculties.values().stream().filter(value -> value.getColor().equals(color)).toList();
    }
}
