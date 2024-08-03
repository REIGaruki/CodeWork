package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    public Faculty createFaculty(String name, String color) {
       return facultyRepository.save(new Faculty(name, color));
    }
    public List<Faculty> getAllSFaculties() {
        return facultyRepository.findAll();
    }
    public Faculty readFaculty(Long id) {
        return facultyRepository.findById(id).get();
    }
    public Faculty updateFaculty(Long id, String name, String color) {
        if (facultyRepository.findById(id).isEmpty()) {
            return null;
        }
        facultyRepository.findById(id).get().setName(name);
        facultyRepository.findById(id).get().setColor(color);
        return facultyRepository.findById(id).get();
    }
    public Faculty deleteFaculty(Long id) {
        if (facultyRepository.findById(id).isEmpty()) {
            return null;
        }
        Faculty faculty = facultyRepository.findById(id).get();
        facultyRepository.deleteById(id);
        return faculty;
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findAll().stream().filter(value -> value.getColor().equals(color)).toList();
    }
}
