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
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FacultyControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void clearDatabase() {
        facultyRepository.deleteAll();
    }
    private final String NAME = "tree";

    private final String COLOR = "blue";

    private final String NEW_NAME = "not tree";

    private final String NEW_COLOR = "not blue";


    @Test
    @DisplayName("should create faculty")
    void postTest() {
        String url = "http://localhost:" + port + "/faculties?name=" + NAME + "&color=" + COLOR;
        Faculty faculty = new Faculty(NAME, COLOR);
        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);
        ResponseEntity<Faculty> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.POST,
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
    @DisplayName("should update faculty")
    void putTest() {
        Faculty faculty = new Faculty(NAME, COLOR);
        facultyRepository.save(faculty);
        String url = "http://localhost:" + port + "/faculties/"
                + faculty.getId() + "?name=" + NEW_NAME + "&color=" + NEW_COLOR;
        Faculty updFaculty = new Faculty(NEW_NAME, NEW_COLOR);
        HttpEntity<Faculty> entity = new HttpEntity<>(updFaculty);
        ResponseEntity<Faculty> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                Faculty.class
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Faculty actual = responseEntity.getBody();
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(actual.getName(), updFaculty.getName());
        Assertions.assertEquals(actual.getColor(), updFaculty.getColor());
    }

    @Test
    @DisplayName("should delete faculty")
    void delTest() {
        Faculty faculty = new Faculty(NAME, COLOR);
        facultyRepository.save(faculty);
        String url = "http://localhost:" + port + "/faculties/" + faculty.getId();
        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);
        ResponseEntity<Faculty> delEntity = testRestTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                Faculty.class
        );
        Assertions.assertNotNull(delEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), delEntity.getStatusCode());
        Faculty actual = delEntity.getBody();
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(actual.getName(), faculty.getName());
        Assertions.assertEquals(actual.getColor(), faculty.getColor());
        ResponseEntity<Faculty> getEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Faculty.class
        );
        Assertions.assertEquals(HttpStatusCode.valueOf(404), getEntity.getStatusCode());
    }

    @Test
    @DisplayName("should find faculty")
    void getTest() {
        Faculty faculty = new Faculty(NAME, COLOR);
        facultyRepository.save(faculty);
        String url = "http://localhost:" + port + "/faculties/" + faculty.getId();
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
    @DisplayName("should find all faculties")
    void getAllTest() {
        Faculty faculty = new Faculty(NAME, COLOR);
        facultyRepository.save(faculty);
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(faculty);
        String url = "http://localhost:" + port + "/faculties/all";
        HttpEntity<List<Faculty>> entity = new HttpEntity<>(faculties);
        ResponseEntity<List<Faculty>> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Faculty>>() {}
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        List<Faculty> actual = responseEntity.getBody();
        Assertions.assertNotNull(actual.get(0));
        Assertions.assertEquals(actual.get(0).getName(), faculties.get(0).getName());
        Assertions.assertEquals(actual.get(0).getColor(), faculties.get(0).getColor());
    }

    @Test
    @DisplayName("should find faculties by color")
    void getColorTest() {
        Faculty faculty = new Faculty(NAME, COLOR);
        Faculty newFaculty = new Faculty(NEW_NAME, COLOR);
        facultyRepository.save(faculty);
        facultyRepository.save(newFaculty);
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(faculty);
        faculties.add(newFaculty);
        String url = "http://localhost:" + port + "/faculties?color=" + COLOR;
        HttpEntity<List<Faculty>> entity = new HttpEntity<>(faculties);
        ResponseEntity<List<Faculty>> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Faculty>>() {}
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        List<Faculty> actual = responseEntity.getBody();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.get(0).getName(), faculties.get(0).getName());
        Assertions.assertEquals(actual.get(0).getColor(), faculties.get(0).getColor());
        Assertions.assertEquals(actual.get(1).getName(), faculties.get(1).getName());
        Assertions.assertEquals(actual.get(0).getColor(), faculties.get(1).getColor());
    }

    @Test
    @DisplayName("should find faculties by name and color")
    void getNameColorTest() {
        Faculty faculty = new Faculty(NAME, COLOR);
        Faculty newFaculty = new Faculty(NEW_NAME, NEW_COLOR);
        facultyRepository.save(faculty);
        facultyRepository.save(newFaculty);
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(faculty);
        faculties.add(newFaculty);
        String url = "http://localhost:" + port + "/faculties/find?name=" + NEW_NAME + "&color=" + NEW_COLOR;
        HttpEntity<List<Faculty>> entity = new HttpEntity<>(faculties);
        ResponseEntity<List<Faculty>> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Faculty>>() {}
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        List<Faculty> actual = responseEntity.getBody();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.get(0).getName(), faculties.get(1).getName());
        Assertions.assertEquals(actual.get(0).getColor(), faculties.get(1).getColor());
        Assertions.assertEquals(actual.size(), 1);
    }

    @Test
    @DisplayName("should find student")
    void getStudentsTest() {
        Faculty faculty = new Faculty(NAME, COLOR);
        facultyRepository.save(faculty);
        List<Student> students = new ArrayList<>();
        Student student = new Student("NAME", 13);
        student.setFaculty(faculty);
        Student newStudent = new Student("NEW_NAME", 15);
        newStudent.setFaculty(faculty);
        studentRepository.save(student);
        studentRepository.save(newStudent);
        students.add(student);
        students.add(newStudent);
        String url = "http://localhost:" + port + "/faculties/students_of/" + faculty.getId();
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
        Assertions.assertEquals(actual.get(0).getName(), students.get(0).getName());
        Assertions.assertEquals(actual.get(0).getAge(), students.get(0).getAge());
        Assertions.assertEquals(actual.get(1).getName(), students.get(1).getName());
        Assertions.assertEquals(actual.get(1).getAge(), students.get(1).getAge());
    }

}
