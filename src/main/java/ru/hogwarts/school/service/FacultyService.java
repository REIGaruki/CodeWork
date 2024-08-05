package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(String name, String color) {
       return facultyRepository.save(new Faculty(name, color));
    }

    public List<Faculty> getAllSFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty readFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Long id, String name, String color) {
        return facultyRepository.findById(id).map(faculty -> {
        faculty.setName(name);
        faculty.setColor(color);
        facultyRepository.save(faculty);
        return faculty;
        }).orElse(null);
    }

    public Faculty deleteFaculty(Long id) {
        return facultyRepository.findById(id).map(faculty -> {
            facultyRepository.deleteById(id);
            return faculty;
        }).orElse(null);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findAll().stream().filter(value -> value.getColor().equals(color)).toList();
    }

}
