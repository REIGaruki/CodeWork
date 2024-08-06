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
        Faculty faculty;
        try {
            faculty = readFaculty(id);
        } catch (NullPointerException e) {
            return null;
        }
        faculty.setName(name);
        faculty.setColor(color);
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(Long id) {
        Faculty faculty;
        try {
            faculty = readFaculty(id);
        } catch (NullPointerException e) {
            return null;
        }
        facultyRepository.deleteById(id);
        return faculty;
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findFacultiesByColor(color);
    }

    public List<Faculty> getFacultiesByColorOrName(String name, String color) {
        return facultyRepository.findFacultiesByNameOrColorIgnoreCase(name, color);
    }
}
