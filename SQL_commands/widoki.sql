--
-- Widok do sprawdzania stanu konta
--

CREATE VIEW clients_info_view AS
SELECT
	clients.id AS "ID",
    clients.first_name AS "Imię",
    clients.last_name AS "Nazwisko",
    clients.address AS "Adres",
    clients.city AS "Miasto",
    accounts.balance AS "Saldo",
    accounts.account_number AS "Numer konta"
FROM 
	clients
JOIN
	accounts ON accounts.client_id = clients.id
GROUP BY
	clients.id, accounts.account_number;
    
SELECT * FROM clients_info_view;
    

CREATE VIEW check_balance_view AS 
SELECT
	CONCAT(clients.first_name, ' ', clients.last_name) AS "Imie i nazwisko",
	accounts.account_number AS "Numer konta", 
    accounts.balance AS "Saldo"
FROM 
	accounts
JOIN 
	clients ON clients.id = accounts.client_id
GROUP BY
	clients.id, "Imie i nazwisko", accounts.account_number;
    
SELECT * FROM check_balance_view;

--
-- Widok do sprawdzania ilosci kart
--

CREATE VIEW client_cards_view AS
SELECT
    CONCAT(clients.first_name, ' ', clients.last_name) AS "Imie i nazwisko",
    COUNT(credit_card.client_id) AS "Ilosc kart"
FROM
    clients
JOIN
    credit_card ON clients.id = credit_card.client_id
GROUP BY
    clients.id, "Imie i nazwisko";


SELECT * FROM client_cards_view;

--
-- Widok do sprawdzenia informacji o klientach
--

CREATE VIEW client_credentials_view AS
SELECT
    CONCAT(clients.first_name, ' ', clients.last_name) AS "Imie i nazwisko",
    CONCAT(clients.address, ' ', clients.city) AS "Adres zamieszkania",
    GROUP_CONCAT(accounts.account_number) AS "Numer konta"
FROM
    clients
JOIN
    accounts ON clients.id = accounts.client_id
GROUP BY
    clients.id, "Imie i nazwisko", "Adres zamieszkania";
    
SELECT * FROM client_credentials_view;

--
-- Widok do wyswietlania transakcji
--

CREATE VIEW transactions_view AS
SELECT
    MAX(transaction_type.transaction_name) AS "Rodzaj transakcji",
    transactions.amount AS "Kwota",
    accounts.account_number AS "Numer konta"
FROM 
    transactions
JOIN
    transaction_type ON transactions.type_id = transaction_type.id
JOIN
    accounts ON accounts.id = transactions.account_id
GROUP BY
    transactions.amount, accounts.account_number;
    
--
-- Widok do wyswietlana wydziałów
--

CREATE VIEW departments_view AS
SELECT
    departments.department_name AS "Oddzial",
    departments.address AS "Adres",
    COUNT(employees.id) AS "Liczba pracownikow"
FROM 
    departments
JOIN
    employees ON departments.id = employees.department_id
GROUP BY
    departments.department_name, departments.address;
    
SELECT * FROM departments_view;

--
-- Indeksy
--

CREATE INDEX client_id_index ON clients(id);
CREATE INDEX employee_id_index ON employees(id);
CREATE INDEX card_number_index ON credit_card(card_number);
CREATE INDEX card_producent_index ON credit_card(producent_name);

CREATE INDEX transaction_id_index ON transactions(id);
CREATE INDEX transaction_amount_index ON transactions(amount);
CREATE INDEX transaction_type_index ON transactions(type_id);
CREATE INDEX transaction_account_index ON transactions(account_id);

CREATE INDEX department_name_index ON departments(department_name);
CREATE INDEX department_id_index ON departments(id);

CREATE INDEX account_number_index ON accounts(account_number);

--
-- Usuniecie widokow
--


DROP VIEW departments_view;
DROP VIEW transactions_view;
DROP VIEW client_cards_view;
DROP VIEW check_balance_view;
DROP VIEW client_credentials_view;

--
-- Usuniecie indeksow
--

DROP INDEX client_id_index ON clients;
DROP INDEX employee_id_index ON employees;
DROP INDEX card_number_index ON credit_card;
DROP INDEX card_producent_index ON credit_card;

DROP INDEX transaction_id ON transactions;
DROP INDEX transaction_amount ON transactions;
DROP INDEX transaction_type_index ON transactions;
DROP INDEX transaction_account ON transactions;

DROP INDEX department_name_index ON departments;
DROP INDEX department_id_index ON departments;

DROP INDEX account_number_index ON accounts;