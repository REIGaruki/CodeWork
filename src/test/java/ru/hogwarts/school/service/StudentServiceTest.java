package ru.hogwarts.school.service;

import org.junit.jupiter.api.*;
import ru.hogwarts.school.model.Student;

import java.util.*;

class StudentServiceTest {
    private final Map<Long, Student> students = new HashMap<>();
    StudentService service;
    @BeforeEach
    void init() {
        service = new StudentService();
        service.createStudent("Name1", 11);
        service.createStudent("Name2", 11);
        service.createStudent("Name3", 11);
        service.createStudent("Name4", 12);
        service.createStudent("Name5", 12);
        students.put(1L, new Student(1L,"Name1", 11));
        students.put(2L, new Student(2L,"Name2", 11));
        students.put(3L, new Student(3L,"Name3", 11));
        students.put(4L, new Student(4L,"Name4", 12));
        students.put(5L, new Student(5L,"Name5", 12));
    }
    @AfterEach
    void post() {
        students.remove(1L);
        students.remove(2L);
        students.remove(3L);
        students.remove(4L);
        students.remove(5L);
        students.remove(6L);
    }
    @Test
    @DisplayName("Should get list of all students")
    void getAllStudents() {
        Assertions.assertEquals(students.values().stream().toList(), service.getAllStudents());
    }

    @Test
    @DisplayName("Should create new student")
    void createStudent() {
        students.put(6L, new Student(6L,"Name6", 12));
        Student expected = new Student(6L, "Name6", 12);
        Student actual = service.createStudent("Name6", 12);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(students.values().stream().toList(), service.getAllStudents());
    }

    @Test
    @DisplayName("Should get student info")
    void readStudent() {
        Student expected = new Student(5L, "Name5", 12);
        Student actual = service.readStudent(5L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should update student info")
    void updateStudent() {
        Student expected = new Student(5L, "Name5U", 13);
        Student actual = service.updateStudent(5L, "Name5U", 13);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, service.readStudent(5L));
    }

    @Test
    @DisplayName("Should delete a student")
    void deleteStudent() {
        students.remove(1L);
        Student expected = new Student(1L, "Name1", 11);
        Student actual = service.deleteStudent(1L);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(students.values().stream().toList(), service.getAllStudents());
    }

    @Test
    @DisplayName("Should get all students of same age")
    void getStudentsByAge() {
        List<Student> expected = new ArrayList<Student>(Arrays.asList(
                new Student(1L,"Name1", 11),
                new Student(2L,"Name2", 11),
                new Student(3L,"Name3", 11)
        ));
        List<Student> actual = service.getStudentsByAge(11);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Should return null if such id not exist")
    void getNotFound() {
        Assertions.assertNull(service.readStudent(6L));
        Assertions.assertNull(service.deleteStudent(6L));
        Assertions.assertNull(service.updateStudent(6L, "Name6", 13));
    }
}