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
        if (facultyRepository.findById(id) == null) {
            return null;
        }
        return facultyRepository.findById(id).get();
    }
    public Faculty updateFaculty(Long id, String name, String color) {
        if (facultyRepository.findById(id) == null) {
            return null;
        }
        facultyRepository.findById(id).get().setName(name);
        facultyRepository.findById(id).get().setColor(color);
        return facultyRepository.findById(id).get();
    }
    public Faculty deleteFaculty(Long id) {
        if (facultyRepository.findById(id) == null) {
            return null;
        }
        Faculty faculty = facultyRepository.findById(id).get();
        facultyRepository.deleteById(id);
        return faculty;
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findFacultiesByColor(color);
    }
}
