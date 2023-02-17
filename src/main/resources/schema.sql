CREATE TABLE if not exists customer (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);
CREATE TABLE if not exists person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    age int
);
CREATE TABLE if not exists department (
    id SERIAL PRIMARY KEY,
    dept VARCHAR(255),
    emp_id int
);