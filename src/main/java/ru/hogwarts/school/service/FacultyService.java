package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(String name, String color) {
        logger.info("Was invoked method for create faculty");
       return facultyRepository.save(new Faculty(name, color));
    }

    public List<Faculty> getAllSFaculties() {
        logger.info("Was invoked method for read all faculties");
        return facultyRepository.findAll();
    }

    public Faculty readFaculty(Long id) {
        logger.info("Was invoked method for read faculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public List<Student> readStudents(Long id) {
        logger.info("Was invoked method for read students of faculty");
        return readFaculty(id).getStudents();
    }

    public Faculty updateFaculty(Long id, String name, String color) {
        Optional<Faculty> optionalFaculty= facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            Faculty faculty = optionalFaculty.get();
            faculty.setColor(color);
            faculty.setName(name);
            facultyRepository.save(faculty);
            logger.info("Was invoked method for update faculty");
            return faculty;
        } else {
            logger.error("Was invoked method for create faculty that does not exist");
            return null;
        }
    }

    public Faculty deleteFaculty(Long id) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            Faculty faculty = optionalFaculty.get();
            facultyRepository.deleteById(id);
            logger.info("Was invoked method for delete faculty");
            return faculty;
        } else {
            logger.error("Was invoked method for delete faculty that does not exist");
            return null;
        }
    }

    public List<Faculty> getFacultiesByColorOrName(String name, String color) {
        logger.info("Was invoked method for read faculty by color or name");
        return facultyRepository.findFacultiesByNameOrColorIgnoreCase(name, color);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        logger.info("Was invoked method for read faculties by color");
        return facultyRepository.findFacultiesByColor(color);
    }

}
