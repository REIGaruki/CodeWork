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
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
public class StudentControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    private final String NAME = "Harry";

    private final int AGE = 13;

    private final String NEW_NAME = "Potter";

    private final int NEW_AGE = 15;

    @Test
    @DisplayName("Should create student")
    void postTest() throws Exception {
        Student student = new Student(NAME, AGE);
        Long studentId = 1L;
        Student savedStudent = new Student(NAME, AGE);
        savedStudent.setId(studentId);
        when(studentService.createStudent(student.getName(), student.getAge())).thenReturn(savedStudent);
        ResultActions perform = mockMvc.perform(post("/students?name=" + NAME + "&age=" + AGE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
        perform.andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.name").value(savedStudent.getName()))
                .andExpect(jsonPath("$.age").value(savedStudent.getAge()))
                .andDo(print());
    }

    @Test
    @DisplayName("Should get student")
    void getTest() throws Exception {
        Student student = new Student(NAME, AGE);
        Long studentId = 1L;
        when(studentService.readStudent(studentId)).thenReturn(student);
        ResultActions perform = mockMvc.perform(get("/students/{id}", studentId));
        perform.andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());

    }

    @Test
    @DisplayName("Should get all students")
    void getAllTest() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student(NAME, AGE));
        students.add(new Student(NEW_NAME, NEW_AGE));
        when(studentService.getAllStudents()).thenReturn(students);
        ResultActions perform = mockMvc.perform(get("/students/all"));
        perform.andExpect(jsonPath("$[0].name").value(students.get(0).getName()))
                .andExpect(jsonPath("$[0].age").value(students.get(0).getAge()))
                .andExpect(jsonPath("$[1].name").value(students.get(1).getName()))
                .andExpect(jsonPath("$[1].age").value(students.get(1).getAge()))
                .andDo(print());

    }

    @Test
    @DisplayName("Should get students by age")
    void getAgeTest() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student(NAME, AGE));
        when(studentService.getStudentsByAge(AGE)).thenReturn(students);
        ResultActions perform = mockMvc.perform(get("/students?age=" + AGE));
        perform.andExpect(jsonPath("$[0].name").value(students.get(0).getName()))
                .andExpect(jsonPath("$[0].age").value(students.get(0).getAge()))
                .andDo(print());

    }

    @Test
    @DisplayName("Should get students by age in interval")
    void getIntervalTest() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student(NAME, AGE));
        when(studentService.getStudentsByAgeInterval(AGE, NEW_AGE)).thenReturn(students);
        ResultActions perform = mockMvc.perform(get("/students/age?min=" + AGE + "&max=" + NEW_AGE));
        perform.andExpect(jsonPath("$[0].name").value(students.get(0).getName()))
                .andExpect(jsonPath("$[0].age").value(students.get(0).getAge()))
                .andDo(print());

    }

    @Test
    @DisplayName("Should get fauculty of student")
    void getFacultyTest() throws Exception {
        Student student = new Student(NAME, AGE);
        Long studentId = 1L;
        Faculty faculty = new Faculty("name", "color");
        when(studentService.readFaculty(studentId)).thenReturn(faculty);
        ResultActions perform = mockMvc.perform(get("/students/facultyOf/{id}", studentId));
        perform.andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());

    }

    @Test
    @DisplayName("Should update student")
    void putTest() throws Exception {
        Long studentId = 1L;
        Student updatedStudent = new Student(NEW_NAME, NEW_AGE);
        updatedStudent.setId(studentId);
        when(studentService.updateStudent(studentId, NEW_NAME, NEW_AGE)).thenReturn(updatedStudent);
        ResultActions perform = mockMvc
                .perform(put("/students/{id}?name=" + NEW_NAME + "&age=" + NEW_AGE, studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)));
        perform.andExpect(jsonPath("$.id").value(updatedStudent.getId()))
                .andExpect(jsonPath("$.name").value(updatedStudent.getName()))
                .andExpect(jsonPath("$.age").value(updatedStudent.getAge()))
                .andDo(print());
    }

    @Test
    @DisplayName("Should delete student")
    void delTest() throws Exception {
        Student student = new Student(NAME, AGE);
        Long studentId = 1L;
        student.setId(studentId);
        when(studentService.deleteStudent(student.getId())).thenReturn(student);
        ResultActions perform = mockMvc.perform(delete("/students/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
        perform.andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

}
