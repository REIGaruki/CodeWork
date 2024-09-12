package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findStudentsByAge(int age);
    List<Student> findStudentsByAgeBetween(int min, int max);
    List<Student> findStudentsByFacultyId(Long facultyId);

    @Query(value = "SELECT COUNT(id) FROM student", nativeQuery = true)
    Long getStudentsCount();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    int getStudentsAverageAge();

    @Query(value = "SELECT * FROM (SELECT * FROM student ORDER BY id DESC LIMIT :amount) AS last ORDER BY id ASC",
            nativeQuery = true)
    List<Student> getLastOfAmount(@Param("amount") Long amount);
}
