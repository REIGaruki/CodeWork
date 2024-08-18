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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

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
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void clearDatabase() {
        facultyRepository.deleteAll();
    }
    private final String NAME = "tree";

    private final String COLOR = "blue";


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
                + faculty.getId() + "?name=" + NAME + "1&color=" + COLOR + '1';
        Faculty updFaculty = new Faculty(NAME + '1', COLOR + '1');
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

}
