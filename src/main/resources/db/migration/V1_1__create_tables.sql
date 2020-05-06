CREATE TABLE task_types (
  id SERIAL PRIMARY KEY,
  name TEXT
);

CREATE TABLE tasks (
  id SERIAL PRIMARY KEY,
  type_id INTEGER,
  name TEXT,
  input_schema TEXT,
  url TEXT,
  FOREIGN KEY (type_id) REFERENCES task_types(id)
);