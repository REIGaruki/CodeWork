package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(String name, int age) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(new Student(name, age));
    }

    public Student readStudent(Long id) {
        logger.info("Was invoked method for read student");
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getAllStudents() {
        logger.info("Was invoked method for read all students");
        return studentRepository.findAll().stream().toList();
    }

    public Student updateStudent(Long id, String name, int age) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setAge(age);
            student.setName(name);
            studentRepository.save(student);
            logger.info("Was invoked method for update student");
            return student;
        } else {
            logger.warn("Was invoked method for update, but student was not found");
            return null;
        }
    }

    public Student deleteStudent(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            studentRepository.deleteById(id);
            logger.info("Was invoked method for delete student");
            return student;
        } else {
            logger.debug("Was invoked method for delete, but student was not found");
            return null;
        }
    }

    public List<Student> getStudentsByAge(int age) {
        logger.info("Was invoked method for read students by age");
        return studentRepository.findStudentsByAge(age);
    }

    public List<Student> getStudentsByAgeInterval(int min, int max) {
        logger.info("Was invoked method for read students by age interval");
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public List<Student> getStudentsByFacultyId(Long facultyId) {
        logger.info("Was invoked method for read students by faculty");
        return studentRepository.findStudentsByFacultyId(facultyId);
    }

    public Faculty readFaculty(Long id) {
        logger.info("Was invoked method for read student's faculty");
        return readStudent(id).getFaculty();
    }

    public Long getStudentsCount() {
        logger.info("Was invoked method for read students quantity");
        return studentRepository.getStudentsCount();
    }

    public  int getStudentsAverageAge() {
        logger.info("Was invoked method for read students' average age");
        return studentRepository.getStudentsAverageAge();
    }

    public List<Student> getLastOfAmount(Long amount) {
        logger.info("Was invoked method for read last " + amount + " of students");
        return studentRepository.getLastOfAmount(amount);
    }

}
