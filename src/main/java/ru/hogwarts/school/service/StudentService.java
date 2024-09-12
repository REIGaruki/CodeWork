package ru.hogwarts.school.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(String name, int age) {
        return studentRepository.save(new Student(name, age));
    }

    public Student readStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll().stream().toList();
    }

    public Student updateStudent(Long id, String name, int age) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setAge(age);
            student.setName(name);
            studentRepository.save(student);
            return student;
        } else {
            return null;
        }
    }

    public Student deleteStudent(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            studentRepository.deleteById(id);
            return student;
        } else {
            return null;
        }
    }

    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findStudentsByAge(age);
    }

    public List<Student> getStudentsByAgeInterval(int min, int max) {
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public List<Student> getStudentsByFacultyId(Long facultyId) {
        return studentRepository.findStudentsByFacultyId(facultyId);
    }

    public Faculty readFaculty(Long id) {
        return readStudent(id).getFaculty();
    }

    public Long getStudentsCount() {
        return studentRepository.getStudentsCount();
    }

    public  int getStudentsAverageAge() {
        return studentRepository.getStudentsAverageAge();
    }

    public List<Student> getLastOfAmount(Long amount) {
        return studentRepository.getLastOfAmount(amount);
    }

    public List<String> getStudentsNamesAlphabeticalInitialSorted(String initial) {
//        logger.info("Was invoked method for read students in alphabetical order by letter '" + initial + "'");
        return studentRepository.findAll()
                .stream()
                .parallel()
                .map(Student::getName)
                .map(StringUtils::capitalize)
                .filter(name -> name.startsWith(StringUtils.capitalize(initial)))
                .sorted()
                .toList();
    }

    public int getStudentsAverageAgeStream() {
        return (int) studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .summaryStatistics().getAverage();
    }

}
