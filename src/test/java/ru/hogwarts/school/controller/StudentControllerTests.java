package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

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

}
