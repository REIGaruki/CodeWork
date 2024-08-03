package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.readStudent(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(student);
    }
    @GetMapping("all")
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }
    @GetMapping
    public ResponseEntity<List<Student>> getStudentsByAge(@RequestParam int age) {
        if (age <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(studentService.getStudentsByAge(age));
    }
    @PutMapping("{id}")
    public ResponseEntity<Student> putStudent(@PathVariable Long id, @RequestParam String name, @RequestParam int age) {
        Student student = studentService.updateStudent(id, name, age);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }
    @PostMapping
    public ResponseEntity<Student> postStudent(@RequestParam String name, @RequestParam int age) {
        return ResponseEntity.ok(studentService.createStudent(name, age));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student student = studentService.deleteStudent(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(student);
    }
}
