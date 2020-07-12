CREATE TABLE user_roles (
  id SERIAL PRIMARY KEY,
  alias TEXT,
  name TEXT
);

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  login TEXT,
  password TEXT,
  salt TEXT,
  role_id INTEGER,
  FOREIGN KEY (role_id) REFERENCES user_roles(id)
);