package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService = new StudentService();
    @GetMapping("{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.readStudent(id);
    }
    @GetMapping
    public List<Student> getStudentsByAge(@RequestParam int age) {
        return studentService.getStudentsByAge(age);
    }
    @PutMapping("{id}")
    public Student putStudent(@PathVariable Long id, @RequestParam String name, @RequestParam int age) {
        return studentService.updateStudent(id, name, age);
    }
    @PostMapping
    public Student postStudent(@RequestParam String name, @RequestParam int age) {
        return studentService.createStudent(name, age);
    }
    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}
