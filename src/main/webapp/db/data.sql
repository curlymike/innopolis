CREATE TABLE person
(
    id         INT NOT NULL AUTO_INCREMENT,
    name       VARCHAR(250),
    birth_date LONG(250),
    login      VARCHAR(40),
    pass       VARCHAR(64),
    UNIQUE (login)
);

INSERT INTO person (name, birth_date, login, pass) VALUES('User', 0, 'user', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');
INSERT INTO person (name, birth_date, login, pass) VALUES('Homer', -430444800000, 'homer', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');
INSERT INTO person (name, birth_date, login, pass) VALUES('Mike', 389059200000, 'mike', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');
INSERT INTO person (name, birth_date, login, pass) VALUES('John', 0, 'john', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');
INSERT INTO person (name, birth_date, login, pass) VALUES('Pete', 0, 'pete', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');
INSERT INTO person (name, birth_date, login, pass) VALUES('Helen', 0, 'helen', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');

