package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

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
        Optional<Faculty> optionalFaculty= facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            Faculty faculty = optionalFaculty.get();
            faculty.setColor(color);
            faculty.setName(name);
            facultyRepository.save(faculty);
            return faculty;
        } else {
            return null;
        }
    }

    public Faculty deleteFaculty(Long id) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            Faculty faculty = optionalFaculty.get();
            facultyRepository.deleteById(id);
            return faculty;
        } else {
            return null;
        }
    }

    public List<Faculty> getFacultiesByColorOrName(String name, String color) {
        return facultyRepository.findFacultiesByNameOrColorIgnoreCase(name, color);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findFacultiesByColor(color);
    }

}
