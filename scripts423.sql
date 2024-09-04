SELECT student.name, student.age, faculty.name
FROM student
INNER JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.name, student.age
FROM student
RIGHT JOIN avatar ON student.avatar_id = avatar.id;