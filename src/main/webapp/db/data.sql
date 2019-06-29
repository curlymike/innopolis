CREATE TABLE person
(
    id         INT NOT NULL AUTO_INCREMENT,
    name       VARCHAR(250) NOT NULL,
    birth_date LONG(250),
    login      VARCHAR(40)  NOT NULL,
    email      VARCHAR(128) NOT NULL DEFAULT '',
    phone      VARCHAR(16)  NOT NULL DEFAULT '',
    pass       VARCHAR(64)  NOT NULL,
    UNIQUE (login)
);

INSERT INTO person (name, birth_date, login, email, phone, pass) VALUES('User', 0, 'user', 'user@somehost.ru', '78432039253', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');
INSERT INTO person (name, birth_date, login, email, phone, pass) VALUES('Homer', -430444800000, 'homer', 'chunkylover53@aol.com', '19395550113', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');
INSERT INTO person (name, birth_date, login, email, phone, pass) VALUES('Mike', 389059200000, 'mike', 'mike@somehost.com', '79110000000', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');
INSERT INTO person (name, birth_date, login, pass) VALUES('John', 0, 'john', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');
INSERT INTO person (name, birth_date, login, pass) VALUES('Pete', 0, 'pete', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');
INSERT INTO person (name, birth_date, login, pass) VALUES('Helen', 0, 'helen', 'a0aad2fad866b15ab14121b3f6969ee20dbe11cd5e844b0f2c8de63b90f4f2e6');
