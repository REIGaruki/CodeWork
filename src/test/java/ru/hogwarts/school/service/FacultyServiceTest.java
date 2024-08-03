package ru.hogwarts.school.service;

import org.junit.jupiter.api.*;
import ru.hogwarts.school.model.Faculty;

import java.util.*;

class FacultyServiceTest {

    private final Map<Long, Faculty> faculties = new HashMap<>();
    FacultyService service;
    @BeforeEach
    void init() {
        service = new FacultyService();
        service.createFaculty("Name1", "Green");
        service.createFaculty("Name2", "Green");
        service.createFaculty("Name3", "Green");
        service.createFaculty("Name4", "Red");
        service.createFaculty("Name5", "Red");
        faculties.put(1L, new Faculty("Name1", "Green"));
        faculties.put(2L, new Faculty("Name2", "Green"));
        faculties.put(3L, new Faculty("Name3", "Green"));
        faculties.put(4L, new Faculty("Name4", "Red"));
        faculties.put(5L, new Faculty("Name5", "Red"));
    }
    @AfterEach
    void post() {
        faculties.remove(1L);
        faculties.remove(2L);
        faculties.remove(3L);
        faculties.remove(4L);
        faculties.remove(5L);
        faculties.remove(6L);
    }
    @Test
    @DisplayName("Should get list of all students")
    void getAllStudents() {
        Assertions.assertEquals(faculties.values().stream().toList(), service.getAllSFaculties());
    }

    @Test
    @DisplayName("Should create new student")
    void createStudent() {
        faculties.put(6L, new Faculty("Name6", "Red"));
        Faculty expected = new Faculty("Name6", "Red");
        Faculty actual = service.createFaculty("Name6", "Red");
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(faculties.values().stream().toList(), service.getAllSFaculties());
    }

    @Test
    @DisplayName("Should get faculty info")
    void read() {
        Faculty expected = new Faculty("Name5", "Red");
        Faculty actual = service.readFaculty(5L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should update faculty info")
    void update() {
        Faculty expected = new Faculty("Name5U", "Blue");
        Faculty actual = service.updateFaculty(5L, "Name5U", "Blue");
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, service.readFaculty(5L));
    }

    @Test
    @DisplayName("Should delete a student")
    void delete() {
        faculties.remove(1L);
        Faculty expected = new Faculty("Name1", "Green");
        Faculty actual = service.deleteFaculty(1L);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(faculties.values().stream().toList(), service.getAllSFaculties());
    }

    @Test
    @DisplayName("Should get all faculties of same color")
    void getColor() {
        List<Faculty> expected = new ArrayList<>(Arrays.asList(
                new Faculty("Name1", "Green"),
                new Faculty("Name2", "Green"),
                new Faculty("Name3", "Green")
        ));
        List<Faculty> actual = service.getFacultiesByColor("Green");
        Assertions.assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Should return null if such id not exist")
    void getNotFound() {
        Assertions.assertNull(service.readFaculty(6L));
        Assertions.assertNull(service.deleteFaculty(6L));
        Assertions.assertNull(service.updateFaculty(6L, "Name6", "Blue"));
    }
}