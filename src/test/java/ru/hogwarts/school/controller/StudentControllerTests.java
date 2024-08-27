package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StudentControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    void clearDatabase() {
        studentRepository.deleteAll();
    }
    private final String NAME = "Harry";

    private final int AGE = 13;

    private final String NEW_NAME = "Potter";

    private final int NEW_AGE = 15;


    @Test
    @DisplayName("should create student")
    void postTest() {
        String url = "http://localhost:" + port + "/students?name=" + NAME + "&age=" + AGE;
        Student student = new Student(NAME, AGE);
        HttpEntity<Student> entity = new HttpEntity<>(student);
        ResponseEntity<Student> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Student.class
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Student actual = responseEntity.getBody();
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(actual.getName(), student.getName());
        Assertions.assertEquals(actual.getAge(), student.getAge());
    }

    @Test
    @DisplayName("should update student")
    void putTest() {
        Student student = new Student(NAME, AGE);
        studentRepository.save(student);
        String url = "http://localhost:" + port + "/students/"
                + student.getId() + "?name=" + NEW_NAME + "&age=" + NEW_AGE;
        Student updStudent = new Student(NEW_NAME, NEW_AGE);
        HttpEntity<Student> entity = new HttpEntity<>(updStudent);
        ResponseEntity<Student> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                Student.class
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Student actual = responseEntity.getBody();
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(actual.getName(), updStudent.getName());
        Assertions.assertEquals(actual.getAge(), updStudent.getAge());
    }

    @Test
    @DisplayName("should delete student")
    void delTest() {
        Student student = new Student(NAME, AGE);
        studentRepository.save(student);
        String url = "http://localhost:" + port + "/students/" + student.getId();
        HttpEntity<Student> entity = new HttpEntity<>(student);
        ResponseEntity<Student> delEntity = testRestTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                Student.class
        );
        Assertions.assertNotNull(delEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), delEntity.getStatusCode());
        Student actual = delEntity.getBody();
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(actual.getName(), student.getName());
        Assertions.assertEquals(actual.getAge(), student.getAge());
        ResponseEntity<Student> getEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Student.class
        );
        Assertions.assertEquals(HttpStatusCode.valueOf(404), getEntity.getStatusCode());
    }

    @Test
    @DisplayName("should find student")
    void getTest() {
        Student student = new Student(NAME, AGE);
        studentRepository.save(student);
        String url = "http://localhost:" + port + "/students/" + student.getId();
        HttpEntity<Student> entity = new HttpEntity<>(student);
        ResponseEntity<Student> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Student.class
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Student actual = responseEntity.getBody();
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(actual.getName(), student.getName());
        Assertions.assertEquals(actual.getAge(), student.getAge());
    }

    @Test
    @DisplayName("should find all students")
    void getAllTest() {
        Student studentA = new Student(NAME, AGE);
        Student studentB = new Student(NEW_NAME, NEW_AGE);
        studentRepository.save(studentA);
        studentRepository.save(studentB);
        List<Student> students = new ArrayList<>();
        students.add(studentA);
        students.add(studentB);
        String url = "http://localhost:" + port + "/students/all";
        HttpEntity<List<Student>> entity = new HttpEntity<>(students);
        ResponseEntity<List<Student>> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Student>>() {}
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        List<Student> actual = responseEntity.getBody();
        Assertions.assertNotNull(actual.get(0).getId());
        Assertions.assertEquals(actual.get(0).getName(), studentA.getName());
        Assertions.assertEquals(actual.get(0).getAge(), studentA.getAge());
        Assertions.assertNotNull(actual.get(1).getId());
        Assertions.assertEquals(actual.get(1).getName(), studentB.getName());
        Assertions.assertEquals(actual.get(1).getAge(), studentB.getAge());
    }

    @Test
    @DisplayName("should find all students with same age")
    void getByAgeTest() {
        Student studentA = new Student(NAME, AGE);
        Student studentB = new Student(NEW_NAME, NEW_AGE);
        studentRepository.save(studentA);
        studentRepository.save(studentB);
        List<Student> students = new ArrayList<>();
        students.add(studentA);
        students.add(studentB);
        String url = "http://localhost:" + port + "//students?age=" + AGE;
        HttpEntity<List<Student>> entity = new HttpEntity<>(students);
        ResponseEntity<List<Student>> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Student>>() {}
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        List<Student> actual = responseEntity.getBody();
        Assertions.assertNotNull(actual.get(0).getId());
        Assertions.assertEquals(actual.get(0).getName(), studentA.getName());
        Assertions.assertEquals(actual.get(0).getAge(), studentA.getAge());
        Assertions.assertEquals(actual.size(),1);
    }

    @Test
    @DisplayName("should find all students with age of interval")
    void getByAgeIntervalTest() {
        Student studentA = new Student(NAME, AGE);
        Student studentB = new Student(NEW_NAME, NEW_AGE);
        studentRepository.save(studentA);
        studentRepository.save(studentB);
        List<Student> students = new ArrayList<>();
        students.add(studentA);
        students.add(studentB);
        int minAge = AGE + 1;
        String url = "http://localhost:" + port + "//students/age?min=" + minAge + "&max=" + NEW_AGE;
        HttpEntity<List<Student>> entity = new HttpEntity<>(students);
        ResponseEntity<List<Student>> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Student>>() {}
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        List<Student> actual = responseEntity.getBody();
        Assertions.assertNotNull(actual.get(0).getId());
        Assertions.assertEquals(actual.get(0).getName(), studentB.getName());
        Assertions.assertEquals(actual.get(0).getAge(), studentB.getAge());
        Assertions.assertEquals(actual.size(),1);
    }

    @Test
    @DisplayName("should find student's faculty")
    void getFacultyTest() {
        Faculty faculty = new Faculty("NAME", "COLOR");
        facultyRepository.save(faculty);
        Student student = new Student(NAME, AGE);
        student.setFaculty(faculty);
        studentRepository.save(student);
        String url = "http://localhost:" + port + "/students/facultyOf/" + student.getId();
        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);
        ResponseEntity<Faculty> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Faculty.class
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Faculty actual = responseEntity.getBody();
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(actual.getName(), faculty.getName());
        Assertions.assertEquals(actual.getColor(), faculty.getColor());
    }

    @Test
    @DisplayName("Should get quantity of students")
    void getStudentsCount() {
        Student studentA = new Student(NAME, AGE);
        Student studentB = new Student(NEW_NAME, NEW_AGE);
        studentRepository.save(studentA);
        studentRepository.save(studentB);
        List<Student> students = new ArrayList<>();
        students.add(studentA);
        students.add(studentB);
        String url = "http://localhost:" + port + "/students/count";
        HttpEntity<List<Student>> entity = new HttpEntity<>(students);
        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Long.class
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Long actual = responseEntity.getBody();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual, students.size());
    }

    @Test
    @DisplayName("Should get average age of students")
    void getAvgAge() {
        Student studentA = new Student(NAME, AGE);
        Student studentB = new Student(NEW_NAME, NEW_AGE);
        studentRepository.save(studentA);
        studentRepository.save(studentB);
        List<Student> students = new ArrayList<>();
        students.add(studentA);
        students.add(studentB);
        int avgAge = (students.stream().mapToInt(Student::getAge).sum()) / students.size();
        String url = "http://localhost:" + port + "/students/avg-age";
        HttpEntity<List<Student>> entity = new HttpEntity<>(students);
        ResponseEntity<Integer> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Integer.class
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Integer actual = responseEntity.getBody();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual, avgAge);
    }

    @Test
    @DisplayName("Should get amount of students of the end of the table")
    void getLastTest() {
        Long amount = 1L;
        Student studentA = new Student(NAME, AGE);
        Student studentB = new Student(NEW_NAME, NEW_AGE);
        studentRepository.save(studentA);
        studentRepository.save(studentB);
        List<Student> students = new ArrayList<>();
        students.add(studentA);
        students.add(studentB);
        String url = "http://localhost:" + port + "/students/last/" + amount;
        HttpEntity<List<Student>> entity = new HttpEntity<>(students);
        ResponseEntity<List<Student>> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Student>>() {}
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        List<Student> actual = responseEntity.getBody();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.size(),amount);
        Assertions.assertNotNull(actual.get(0).getId());
        Assertions.assertEquals(actual.get(0).getName(), studentB.getName());
        Assertions.assertEquals(actual.get(0).getAge(), studentB.getAge());
    }

}
