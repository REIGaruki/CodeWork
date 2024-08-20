package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

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
