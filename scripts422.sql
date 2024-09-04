CREATE TABLE driver (
    id SERIAL,
    name VARCHAR,
    age INTEGER,
    license BOOLEAN
    car_id BIGINT REFERENCES car(id)
);
CREATE TABLE car (
    id SERIAL,
    brand VARCHAR,
    model VARCHAR,
    cost NUMERIC(12,2)
);