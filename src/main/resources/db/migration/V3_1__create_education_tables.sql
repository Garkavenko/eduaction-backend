CREATE TABLE disciplines (
  id SERIAL PRIMARY KEY,
  name TEXT
);

CREATE TABLE discipline_profiles (
    id SERIAL PRIMARY KEY,
    name TEXT,
    discipline_id INTEGER,
    FOREIGN KEY (discipline_id) REFERENCES disciplines(id)
);

CREATE TABLE seasons (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE course_work_template (
    id SERIAL PRIMARY KEY,
    discipline_id INTEGER,
    discipline_profile_id INTEGER,
    year_number INTEGER,
    season_id INTEGER,
    user_id INTEGER,
    FOREIGN KEY (discipline_id) REFERENCES disciplines(id),
    FOREIGN KEY (discipline_profile_id) REFERENCES discipline_profiles(id),
    FOREIGN KEY (season_id) REFERENCES seasons(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE tasks_to_course_work_template (
    id SERIAL PRIMARY KEY,
    task_id INTEGER,
    course_work_template_id INTEGER,
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    FOREIGN KEY (course_work_template_id) REFERENCES course_work_template(id)
)