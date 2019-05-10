DROP SCHEMA IF EXISTS idonate;
CREATE SCHEMA idonate;

USE idonate;

CREATE TABLE hospitals(
    id VARCHAR(64) NOT NULL,
    password VARCHAR(128) NOT NULL,
    name VARCHAR(128) NOT NULL,
    address VARCHAR(256) NOT NULL,
    phone VARCHAR(16) NOT NULL,
    email VARCHAR(256) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE people(
    cpf VARCHAR(16) NOT NULL,
    name VARCHAR(128) NOT NULL,
    address VARCHAR(256) NOT NULL,
    phone VARCHAR(16) NOT NULL,
    email VARCHAR(256) NOT NULL,
    age INT NOT NULL,
    sex INT NOT NULL,
    weight FLOAT NOT NULL,
    blood_type INT NOT NULL,
    medical_conditions TEXT NOT NULL,
    hospital_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (cpf),
    CONSTRAINT fk_person_hospital_id FOREIGN KEY (hospital_id) REFERENCES hospitals(id)
                                                               ON UPDATE CASCADE
);

CREATE TABLE resources(
    id INT NOT NULL AUTO_INCREMENT,
    donor_cpf VARCHAR(16) NOT NULL,
    donation_date DATE NOT NULL,
    description TEXT NOT NULL,
    acceptor_cpf VARCHAR(16) NULL DEFAULT NULL,
    acceptation_date DATE NULL DEFAULT NULL,
    type INT NOT NULL,
    PRIMARY KEY (id, donor_cpf),
    CONSTRAINT fk_resource_donor_cpf FOREIGN KEY (donor_cpf) REFERENCES people(cpf)
                                                             ON UPDATE CASCADE,
    CONSTRAINT fk_resource_acceptor_cpf FOREIGN KEY (acceptor_cpf) REFERENCES people(cpf)
);

CREATE TABLE organs(
    resource_id INT NOT NULL,
    type INT NOT NULL,
    weight FLOAT NOT NULL,
    PRIMARY KEY (resource_id),
    CONSTRAINT fk_organ_resource_id FOREIGN KEY (resource_id) REFERENCES resources(id)
                                                              ON DELETE CASCADE
                                                              ON UPDATE CASCADE
);

CREATE TABLE bloods(
    resource_id INT NOT NULL,
    type INT NOT NULL,
    volume FLOAT NOT NULL,
    PRIMARY KEY (resource_id),
    CONSTRAINT fk_blood_resource_id FOREIGN KEY (resource_id) REFERENCES resources(id)
                                                              ON DELETE CASCADE
                                                              ON UPDATE CASCADE
);

CREATE TABLE bone_marrows(
    resource_id INT NOT NULL,
    hla VARCHAR(32) NOT NULL,
    redome VARCHAR(32) NOT NULL,
    PRIMARY KEY (resource_id),
    CONSTRAINT fk_bone_marrow_resource_id FOREIGN KEY (resource_id) REFERENCES resources(id)
                                                                    ON DELETE CASCADE
                                                                    ON UPDATE CASCADE
);