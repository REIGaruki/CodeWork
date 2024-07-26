package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

@RestController
@RequestMapping("faculties")
public class FacultyController {
    private final FacultyService facultyService = new FacultyService();
    @PostMapping
    public Faculty postFaculty(@RequestParam String name, @RequestParam String color) {
        return facultyService.createFaculty(name, color);
    }
    @GetMapping("{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return facultyService.readFaculty(id);
    }
    @PutMapping("{id}")
    public Faculty putFaculty(@PathVariable Long id, @RequestParam String name, @RequestParam String color) {
        return facultyService.updateFaculty(id, name, color);
    }
    @DeleteMapping("{id}")
    public Faculty deleteFaculty(@PathVariable Long id) {
        return facultyService.deleteFaculty(id);
    }
}
