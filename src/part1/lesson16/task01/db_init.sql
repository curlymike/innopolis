DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TYPE IF EXISTS roles;
DROP TABLE IF EXISTS logs;

-- Таблица USER содержит поля id, name, birthday, login_ID, city, email, description
-- Таблица ROLE содержит поля id, name (принимает значения Administration, Clients, Billing), description
-- Таблица USER_ROLE содержит поля id, user_id, role_id

CREATE TABLE "user"
(
  id               SERIAL PRIMARY KEY,
  name             VARCHAR(255)            NOT NULL,
  birthday         TIMESTAMP DEFAULT now() NOT NULL,
  login_ID         VARCHAR(64)             NOT NULL,
  city             VARCHAR(255)            NOT NULL,
  email            VARCHAR(255)            NOT NULL,
  description      TEXT                    DEFAULT '',
  UNIQUE(login_ID)
);

CREATE TYPE roles AS ENUM ('Administration', 'Clients', 'Billing');

CREATE TABLE role
(
  id               SERIAL PRIMARY KEY,
  name             roles,
  description      TEXT DEFAULT ''
);

CREATE TABLE user_role
(
  id               SERIAL PRIMARY KEY,
  user_id          INTEGER REFERENCES "user" (id) ON DELETE CASCADE,
  role_id          INTEGER REFERENCES role (id) ON DELETE CASCADE,
  UNIQUE (user_id, role_id)
);

ALTER SEQUENCE user_role_id_seq RESTART WITH 100;

---

INSERT INTO role (id, name, description) VALUES (1, 'Administration', 'Административный персонал');
INSERT INTO role (id, name, description) VALUES (2, 'Clients', 'Клиенты');
INSERT INTO role (id, name, description) VALUES (3, 'Billing', 'Биллинг');

---

-- колонки ID, DATE, LOG_LEVEL, MESSAGE, EXCEPTION
CREATE TABLE logs
(
  id           SERIAL PRIMARY KEY,
  date         TIMESTAMP DEFAULT now()  NOT NULL,
  log_level    VARCHAR(64)              NOT NULL,
  message      TEXT DEFAULT ''          NOT NULL,
  exception    TEXT DEFAULT ''          NOT NULL
);

CREATE INDEX log_level ON logs (log_level);
CREATE INDEX date ON logs (date);
