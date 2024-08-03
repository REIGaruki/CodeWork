package ru.hogwarts.school.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {


    @Mock
    private FacultyRepository repositoryMock;
    private final Map<Long, Faculty> faculties = new HashMap<>();
    @InjectMocks
    FacultyService service;
    @BeforeEach
    void init() {
        service = new FacultyService(repositoryMock);
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
        when(repositoryMock.findAll()).thenReturn(faculties.values().stream().toList());
        Assertions.assertEquals(faculties.values().stream().toList(), service.getAllSFaculties());
    }

    @Test
    @DisplayName("Should create new student")
    void createStudent() {
        faculties.put(6L, new Faculty("Name6", "Red"));
        Faculty expected = new Faculty("Name6", "Red");
        when(repositoryMock.save(expected)).thenReturn(expected);
        Faculty actual = service.createFaculty("Name6", "Red");
        Assertions.assertEquals(expected, actual);
        when(repositoryMock.findAll()).thenReturn(faculties.values().stream().toList());
        Assertions.assertEquals(faculties.values().stream().toList(), service.getAllSFaculties());
    }

    @Test
    @DisplayName("Should get faculty info")
    void read() {
        Faculty expected = new Faculty("Name5", "Red");
        when(repositoryMock.findById(5L)).thenReturn(Optional.of(expected));
        Faculty actual = service.readFaculty(5L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should update faculty info")
    void update() {
        Faculty expected = new Faculty("Name5U", "Blue");
        when(repositoryMock.findById(5L)).thenReturn(Optional.of(expected));
        Faculty actual = service.updateFaculty(5L, "Name5U", "Blue");
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, service.readFaculty(5L));
    }

    @Test
    @DisplayName("Should delete a student")
    void delete() {
        faculties.remove(1L);
        Faculty expected = new Faculty("Name1", "Green");
        when(repositoryMock.findById(1L)).thenReturn(Optional.of(expected));
        when(repositoryMock.findAll()).thenReturn(faculties.values().stream().toList());
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
        when(repositoryMock.findFacultiesByColor("Green"))
                .thenReturn(faculties.values().stream().filter(value -> value.getColor().equals("Green")).toList());
        List<Faculty> actual = service.getFacultiesByColor("Green");
        Assertions.assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Should return null if such id not exist")
    void getNotFound() {
        when(repositoryMock.findById(6L)).thenReturn(null);
        Assertions.assertNull(service.readFaculty(6L));
        Assertions.assertNull(service.deleteFaculty(6L));
        Assertions.assertNull(service.updateFaculty(6L, "Name6", "Blue"));
    }
}