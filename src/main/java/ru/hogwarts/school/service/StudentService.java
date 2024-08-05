package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

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
            Student student = studentRepository.findById(id).get();
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
            Student student = studentRepository.findById(id).get();
            studentRepository.deleteById(id);
            return student;
        } else {
            return null;
        }
    }

    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findAll().stream().filter(value -> value.getAge() == age).toList();
    }
}
