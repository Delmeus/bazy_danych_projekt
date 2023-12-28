-- CREATE TRIGGER;
DELIMITER //

CREATE TRIGGER update_balance
AFTER INSERT ON transactions FOR EACH ROW
BEGIN
    DECLARE transaction_amount DECIMAL(15, 2);

    SET transaction_amount = NEW.amount;

    IF MOD(NEW.type_id, 2) = 0 THEN
        -- Jeśli ID transakcji jest parzyste, odejmij kwotę
        UPDATE accounts
        SET balance = balance - transaction_amount
        WHERE id = NEW.account_id;
    ELSE
        -- W przeciwnym razie dodaj kwotę
        UPDATE accounts
        SET balance = balance + transaction_amount
        WHERE id = NEW.account_id;
    END IF;
END //

DELIMITER ;

INSERT INTO transactions (amount, type_id, account_id, transaction_date)
VALUES
(46.73, 6, 1, current_date());

DROP TRIGGER update_balance; 

SELECT * FROM accounts;

SELECT * FROM transaction_type;

SELECT * FROM transactions WHERE account_id = 1;

SELECT * FROM transactions_view WHERE `ID klienta` = 1;



