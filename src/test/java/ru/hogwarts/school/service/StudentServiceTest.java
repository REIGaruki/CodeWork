package ru.hogwarts.school.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository repositoryMock;
    private final Map<Long, Student> students = new HashMap<>();
    StudentService service;
    @BeforeEach
    void init() {
        service = new StudentService(repositoryMock);
        students.put(1L, new Student("Name1", 11));
        students.put(2L, new Student("Name2", 11));
        students.put(3L, new Student("Name3", 11));
        students.put(4L, new Student("Name4", 12));
        students.put(5L, new Student("Name5", 12));
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
        when(repositoryMock.findAll()).thenReturn(students.values().stream().toList());
        Assertions.assertEquals(students.values().stream().toList(), service.getAllStudents());
    }

    @Test
    @DisplayName("Should create new student")
    void createStudent() {
        students.put(6L, new Student("Name6", 12));
        Student expected = new Student("Name6", 12);
        when(repositoryMock.save(expected)).thenReturn(expected);
        Student actual = service.createStudent("Name6", 12);
        Assertions.assertEquals(expected, actual);
        when(repositoryMock.findAll()).thenReturn(students.values().stream().toList());
        Assertions.assertEquals(students.values().stream().toList(), service.getAllStudents());
    }

    @Test
    @DisplayName("Should get student info")
    void readStudent() {
        Student expected = new Student("Name5", 12);
        when(repositoryMock.findById(5L)).thenReturn(Optional.of(expected));
        Student actual = service.readStudent(5L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should update student info")
    void updateStudent() {
        Student expected = new Student("Name5U", 13);
        when(repositoryMock.findById(5L)).thenReturn(Optional.of(expected));
        Student actual = service.updateStudent(5L, "Name5U", 13);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, service.readStudent(5L));
    }

    @Test
    @DisplayName("Should delete a student")
    void deleteStudent() {
        students.remove(1L);
        Student expected = new Student("Name1", 11);
        when(repositoryMock.findById(1L)).thenReturn(Optional.of(expected));
        when(repositoryMock.findAll()).thenReturn(students.values().stream().toList());
        Student actual = service.deleteStudent(1L);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(students.values().stream().toList(), service.getAllStudents());
    }

    @Test
    @DisplayName("Should get all students of same age")
    void getStudentsByAge() {
        List<Student> expected = new ArrayList<Student>(Arrays.asList(
                new Student("Name1", 11),
                new Student("Name2", 11),
                new Student("Name3", 11)
        ));
        when(repositoryMock.findAll()).thenReturn(students.values().stream().toList());
        List<Student> actual = service.getStudentsByAge(11);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Should return null if such id not exist")
    void getNotFound() {
        when(repositoryMock.findById(6L)).thenReturn(null);
        Assertions.assertNull(service.readStudent(6L));
        Assertions.assertNull(service.deleteStudent(6L));
        Assertions.assertNull(service.updateStudent(6L, "Name6", 13));
    }
}