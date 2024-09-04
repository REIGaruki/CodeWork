-- liquibase formatted sql

-- changeset agalkin:1
CREATE INDEX student_name_index ON student (name);

-- changeset agalkin:2
CREATE INDEX faculty_nc_idx ON faculty (name, color);
