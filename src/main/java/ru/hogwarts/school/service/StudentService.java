package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private Long idCounter = 0L;
    public Student createStudent(String name, int age) {
        Student student = new Student(++idCounter, name, age);
        students.put(student.getId(), student);
        return student;
    }
    public Student readStudent(Long id) {
        return students.get(id);
    }
    public Student uodateStudent(Long id, String name, int age) {
        students.get(id).setName(name);
        students.get(id).setAge(age);
        return students.get(id);
    }
    public Student deleteStudent(Long id) {
        students.remove(id);
        return students.get(id);
    }
}
