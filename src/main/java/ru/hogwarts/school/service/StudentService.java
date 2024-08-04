package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

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
        Student student;
        try {
            student = readStudent(id);
        } catch (NullPointerException e) {
            return null;
        }
        student.setName(name);
        student.setAge(age);
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        Student student;
        try {
            student = readStudent(id);
        } catch (NullPointerException e) {
            return null;
        }
        studentRepository.deleteById(id);
        return student;
    }

    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findAll().stream().filter(value -> value.getAge() == age).toList();
    }
}
