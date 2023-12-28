--
-- Tworzenie tabel
--

CREATE TABLE clients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    address VARCHAR(90) NOT NULL,
    city VARCHAR(50) NOT NULL
);

CREATE TABLE accounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_number VARCHAR(26) NOT NULL UNIQUE,
    balance DECIMAL(15, 2) NULL,
    client_id INT UNIQUE,
    CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE departments (
    id INT PRIMARY KEY ,
    department_name VARCHAR(30) NOT NULL,
    address VARCHAR(90) NOT NULL
);

CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    position VARCHAR(50) NULL,
    department_id INT NULL,
    FOREIGN KEY  (department_id) REFERENCES departments(id)
);

CREATE TABLE transaction_type (
    id INT PRIMARY KEY AUTO_INCREMENT,
	transaction_name VARCHAR(60) NULL
);

CREATE TABLE transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    amount DECIMAL(15, 2) NULL , 
    type_id INT NULL,
    account_id INT NOT NULL,
    FOREIGN KEY (type_id) REFERENCES transaction_type(id),
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

ALTER TABLE transactions ADD COLUMN transaction_date DATE;

CREATE TABLE Credit_Card (
    id INT PRIMARY KEY AUTO_INCREMENT,
    card_number VARCHAR(16),
    expiry_date DATE NOT NULL,
    client_id INT NOT NULL,
    producent_name VARCHAR(30),
    FOREIGN KEY (client_id) REFERENCES Clients(id)
);

--
-- Usuwanei tabel
--

DROP TABLE credit_card;
DROP TABLE transactions;
DROP TABLE transaction_type;
DROP TABLE employees;
DROP TABLE departments;
DROP TABLE accounts;
DROP TABLE clients;
