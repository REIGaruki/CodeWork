package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FacultyController.class)
public class FacultyControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyService facultyService;

    private final String NAME = "tree";

    private final String COLOR = "blue";

    private final String NEW_NAME = "tree";

    private final String NEW_COLOR = "blue";

    @Test
    @DisplayName("Should create faculty")
    void postTest() throws Exception {
        Faculty faculty = new Faculty(NAME, COLOR);
        Long facultyId = 1L;
        Faculty savedFaculty = new Faculty(NAME, COLOR);
        savedFaculty.setId(facultyId);
        when(facultyService.createFaculty(faculty.getName(), faculty.getColor())).thenReturn(savedFaculty);
        ResultActions perform = mockMvc.perform(post("/faculties?name=" + NAME + "&color=" + COLOR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));
        perform.andExpect(jsonPath("$.id").value(savedFaculty.getId()))
                .andExpect(jsonPath("$.name").value(savedFaculty.getName()))
                .andExpect(jsonPath("$.color").value(savedFaculty.getColor()))
                .andDo(print());
    }

    @Test
    @DisplayName("Should get faculty")
    void getTest() throws Exception {
        Faculty faculty = new Faculty(NAME, COLOR);
        Long facultyId = 1L;
        when(facultyService.readFaculty(facultyId)).thenReturn(faculty);
        ResultActions perform = mockMvc.perform(get("/faculties/{id}", facultyId));
        perform.andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());

    }

    @Test
    @DisplayName("Should get all faculties")
    void getAllTest() throws Exception {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(NAME, COLOR));
        faculties.add(new Faculty(NEW_NAME, NEW_COLOR));
        when(facultyService.getAllSFaculties()).thenReturn(faculties);
        ResultActions perform = mockMvc.perform(get("/faculties/all"));
        perform.andExpect(jsonPath("$[0].name").value(faculties.get(0).getName()))
                .andExpect(jsonPath("$[0].color").value(faculties.get(0).getColor()))
                .andExpect(jsonPath("$[1].name").value(faculties.get(1).getName()))
                .andExpect(jsonPath("$[1].color").value(faculties.get(1).getColor()))
                .andDo(print());

    }

    @Test
    @DisplayName("Should get students of faculty")
    void getStudentsTest() throws Exception {
        Long id = 1L;
        Faculty faculty = new Faculty(NAME, COLOR);
        faculty.setId(id);
        List<Student> students = new ArrayList<>();
        Student student = new Student("NAME", 15);
        student.setFaculty(faculty);
        students.add(student);
        Student newStudent = new Student("NEW_NAME", 6);
        newStudent.setFaculty(faculty);
        students.add(newStudent);
        when(facultyService.readStudents(id)).thenReturn(students);
        ResultActions perform = mockMvc.perform(get("/faculties/students_of/" + id));
        perform.andExpect(jsonPath("$[0].name").value(students.get(0).getName()))
                .andExpect(jsonPath("$[0].age").value(students.get(0).getAge()))
                .andExpect(jsonPath("$[1].name").value(students.get(1).getName()))
                .andExpect(jsonPath("$[1].age").value(students.get(1).getAge()))
                .andDo(print());

    }

    @Test
    @DisplayName("Should get all faculties with same color")
    void getByColorTest() throws Exception {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(NAME, COLOR));
        faculties.add(new Faculty(NEW_NAME, COLOR));
        when(facultyService.getFacultiesByColor(COLOR)).thenReturn(faculties);
        ResultActions perform = mockMvc.perform(get("/faculties?color=" + COLOR));
        perform.andExpect(jsonPath("$[0].name").value(faculties.get(0).getName()))
                .andExpect(jsonPath("$[0].color").value(faculties.get(0).getColor()))
                .andExpect(jsonPath("$[1].name").value(faculties.get(1).getName()))
                .andExpect(jsonPath("$[1].color").value(faculties.get(1).getColor()))
                .andExpect(jsonPath("$[1].color").value(faculties.get(0).getColor()))
                .andDo(print());

    }

    @Test
    @DisplayName("Should get all faculties with same color or name")
    void getByColorOrNameTest() throws Exception {
        List<Faculty> facultiesName = new ArrayList<>();
        List<Faculty> facultiesColor = new ArrayList<>();
        facultiesColor.add(new Faculty(NAME, COLOR));
        facultiesName.add(new Faculty(NEW_NAME, NEW_COLOR));
        when(facultyService.getFacultiesByColorOrName("", COLOR)).
                thenReturn(facultiesColor);
        when(facultyService.getFacultiesByColorOrName(NEW_NAME, "")).
                thenReturn(facultiesName);
        ResultActions performName = mockMvc.perform(get("/faculties/find?name=" + NEW_NAME));
        performName.andExpect(jsonPath("$[0].name").value(facultiesName.get(0).getName()))
                .andExpect(jsonPath("$[0].color").value(facultiesName.get(0).getColor()))
                .andDo(print());
        ResultActions performColor = mockMvc.perform(get("/faculties/find?color=" + COLOR));
        performColor.andExpect(jsonPath("$[0].name").value(facultiesColor.get(0).getName()))
                .andExpect(jsonPath("$[0].color").value(facultiesColor.get(0).getColor()))
                .andDo(print());
    }

    @Test
    @DisplayName("Should update faculty")
    void putTest() throws Exception {
        Long facultyId = 1L;
        Faculty updatedFaculty = new Faculty(NEW_NAME, NEW_COLOR);
        updatedFaculty.setId(facultyId);
        when(facultyService.updateFaculty(facultyId, NEW_NAME, NEW_COLOR)).thenReturn(updatedFaculty);
        ResultActions perform = mockMvc
                .perform(put("/faculties/{id}?name=" + NEW_NAME + "&color=" + NEW_COLOR, facultyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFaculty)));
        perform.andExpect(jsonPath("$.id").value(updatedFaculty.getId()))
                .andExpect(jsonPath("$.name").value(updatedFaculty.getName()))
                .andExpect(jsonPath("$.color").value(updatedFaculty.getColor()))
                .andDo(print());
    }

    @Test
    @DisplayName("Should delete faculty")
    void delTest() throws Exception {
        Faculty faculty = new Faculty(NAME, COLOR);
        Long facultyId = 1L;
        faculty.setId(facultyId);
        when(facultyService.deleteFaculty(faculty.getId())).thenReturn(faculty);
        ResultActions perform = mockMvc.perform(delete("/faculties/{id}", facultyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));
        perform.andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

}
