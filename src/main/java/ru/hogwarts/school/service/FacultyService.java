package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

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
    public Faculty readFaculty(Long id) {
        return faculties.get(id);
    }
    public Faculty updateFaculty(Long id, String name, String color) {
        faculties.get(id).setName(name);
        faculties.get(id).setColor(color);
        return faculties.get(id);
    }
    public Faculty deleteFaculty(Long id) {
        faculties.remove(id);
        return faculties.get(id);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return faculties.values().stream().filter(value -> value.getColor().equals(color)).toList();
    }
}
