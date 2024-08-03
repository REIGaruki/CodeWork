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
        if (studentRepository.findById(id) == null) {
            return null;
        }
        return studentRepository.findById(id).get();
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll().stream().toList();
    }
    public Student updateStudent(Long id, String name, int age) {
        if (studentRepository.findById(id) == null) {
            return null;
        }
        studentRepository.findById(id).get().setName(name);
        studentRepository.findById(id).get().setAge(age);
        return studentRepository.findById(id).get();
    }
    public Student deleteStudent(Long id) {
        if (studentRepository.findById(id) == null) {
            return null;
        }
        Student student = studentRepository.findById(id).get();
        studentRepository.deleteById(id);
        return student;
    }

    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findStudentsByAge(age);
    }
}
