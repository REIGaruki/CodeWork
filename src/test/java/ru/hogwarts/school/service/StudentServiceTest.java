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
    
    private final String NAME_1 = "Name1";
    private final String NAME_2 = "Name2";
    private final String NAME_3 = "Name3";
    private final String NAME_4 = "Name4";
    private final String NAME_5 = "Name5";
    private final String NAME_6 = "Name6";
    private final int AGE_1 = 11;
    private final int AGE_2 = 12;
    private final int AGE_3 = 13;

    StudentService service;

    @BeforeEach
    void init() {
        service = new StudentService(repositoryMock);
        students.put(1L, new Student(NAME_1, AGE_1));
        students.put(2L, new Student(NAME_2, AGE_1));
        students.put(3L, new Student(NAME_3, AGE_1));
        students.put(4L, new Student(NAME_4, AGE_2));
        students.put(5L, new Student(NAME_5, AGE_2));
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
        students.put(6L, new Student(NAME_6, AGE_2));
        Student expected = new Student(NAME_6, AGE_2);
        when(repositoryMock.save(expected)).thenReturn(expected);
        Student actual = service.createStudent(NAME_6, AGE_2);
        Assertions.assertEquals(expected, actual);
        when(repositoryMock.findAll()).thenReturn(students.values().stream().toList());
        Assertions.assertEquals(students.values().stream().toList(), service.getAllStudents());
    }

    @Test
    @DisplayName("Should get student info")
    void readStudent() {
        Student expected = new Student(NAME_5, AGE_2);
        when(repositoryMock.findById(5L)).thenReturn(Optional.of(expected));
        Student actual = service.readStudent(5L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should update student info")
    void updateStudent() {
        Student expected = new Student("Name5U", AGE_3);
        when(repositoryMock.findById(5L)).thenReturn(Optional.of(expected));
        when(repositoryMock.save(expected)).thenReturn(expected);
        Student actual = service.updateStudent(5L, "Name5U", AGE_3);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, service.readStudent(5L));
    }

    @Test
    @DisplayName("Should delete a student")
    void deleteStudent() {
        students.remove(1L);
        Student expected = new Student("Name1", AGE_1);
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
                new Student(NAME_1, AGE_1),
                new Student(NAME_2, AGE_1),
                new Student(NAME_3, AGE_1)
        ));
        when(repositoryMock.findStudentsByAge(AGE_1))
                .thenReturn(students.values().stream().filter(value -> value.getAge() == AGE_1).toList());
        List<Student> actual = service.getStudentsByAge(AGE_1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get all students of age interval")
    void getStudentsByAgeInterval() {
        List<Student> expected = new ArrayList<Student>(Arrays.asList(
                new Student(NAME_1, AGE_1),
                new Student(NAME_2, AGE_1),
                new Student(NAME_3, AGE_1)
        ));
        when(repositoryMock.findStudentsByAgeBetween(AGE_1 - 1, AGE_1))
                .thenReturn(students.values().stream().filter(value -> value.getAge() == AGE_1).toList());
        List<Student> actual = service.getStudentsByAgeInterval(AGE_1 - 1, AGE_1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should throw NPE if such id not exist")
    void getNotFound() {
        when(repositoryMock.findById(6L)).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class,
                () -> service.readStudent(6L));
        Assertions.assertThrows(NullPointerException.class,
                () -> service.deleteStudent(6L));
        Assertions.assertThrows(NullPointerException.class,
                () -> service.updateStudent(6L, NAME_6, 13));
    }

    @Test
    @DisplayName("Should get quantity of students")
    void getCount() {
        Long studentCount = (long) (students.size());
        when(repositoryMock.getStudentsCount()).thenReturn(studentCount);
        Assertions.assertEquals(service.getStudentsCount(), studentCount);
    }

    @Test
    @DisplayName("Should get average age of students")
    void getAvgAge() {
        int avgAge = (AGE_1 * 3 + AGE_2 * 2) / 5;
        when(repositoryMock.getStudentsAverageAge()).thenReturn(avgAge);
        Assertions.assertEquals(avgAge, service.getStudentsAverageAge());
        
    }

    @Test
    @DisplayName("Should get average age of students by stream")
    void getAvgAgeStream() {
        int avgAge = (AGE_1 * 3 + AGE_2 * 2) / 5;
        when(repositoryMock.findAll()).thenReturn(students.values().stream().toList());
        Assertions.assertEquals(avgAge, service.getStudentsAverageAgeStream());

    }

    @Test
    @DisplayName("Should get amount of students of the end of the table")
    void getLastOfAmount() {
        Long amount = 3L;
        Map<Long, Student> lastAmounOfStudents = new HashMap<>();
        lastAmounOfStudents.put(3L, new Student(NAME_3, AGE_1));
        lastAmounOfStudents.put(4L, new Student(NAME_4, AGE_2));
        lastAmounOfStudents.put(5L, new Student(NAME_5, AGE_2));
        List<Student> expected = lastAmounOfStudents.values().stream().toList();
        when(repositoryMock.getLastOfAmount(amount)).thenReturn(expected);
        Assertions.assertEquals(expected, service.getLastOfAmount(amount));
    }

    @Test
    @DisplayName("Should get sorted students of same letter")
    void getSorted() {
        List<String> studentNames = Arrays.asList(NAME_1, NAME_2, NAME_3, NAME_4, NAME_5);
        when(repositoryMock.findAll()).thenReturn(students.values().stream().toList());
        Assertions.assertEquals(studentNames, service.getStudentsNamesAlphabeticalInitialSorted("N"));
    }

}