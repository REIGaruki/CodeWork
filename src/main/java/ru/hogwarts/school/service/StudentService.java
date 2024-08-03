package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
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
        if (!students.containsKey(id)) {
            return null;
        }
        return students.get(id);
    }
    public List<Student> getAllStudents() {
        return students.values().stream().toList();
    }
    public Student updateStudent(Long id, String name, int age) {
        if (!students.containsKey(id)) {
            return null;
        }
        students.get(id).setName(name);
        students.get(id).setAge(age);
        return students.get(id);
    }
    public Student deleteStudent(Long id) {
        if (!students.containsKey(id)) {
            return null;
        }
        Student student = students.get(id);
        students.remove(id);
        return student;
    }

    public List<Student> getStudentsByAge(int age) {
        return students.values().stream().filter(value -> value.getAge() == age).toList();
    }
}
