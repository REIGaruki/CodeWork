package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculties")
public class FacultyController {
    private final FacultyService facultyService = new FacultyService();
    @PostMapping
    public Faculty postFaculty(@RequestParam String name, @RequestParam String color) {
        return facultyService.createFaculty(name, color);
    }
    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.readFaculty(id);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(faculty);
    }
    @GetMapping("color")
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@RequestParam String color) {
        if (color == null || color.isBlank()) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(facultyService.getFacultiesByColor(color));
    }
    @PutMapping("{id}")
    public ResponseEntity<Faculty> putFaculty(@PathVariable Long id, @RequestParam String name, @RequestParam String color) {
        Faculty faculty = facultyService.updateFaculty(id, name, color);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.deleteFaculty(id);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(faculty);
    }
}
