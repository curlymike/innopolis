DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TYPE IF EXISTS roles;

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

---

INSERT INTO role (name, description) VALUES ('Administration', 'Административный персонал');
INSERT INTO role (name, description) VALUES ('Clients', 'Клиенты');
INSERT INTO role (name, description) VALUES ('Billing', 'Биллинг');

/*
CREATE TABLE user_role_alternative
(
  role_id          INTEGER,
  user_id          INTEGER,
  PRIMARY KEY(role_id, user_id)
);
*/
