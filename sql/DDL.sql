CREATE TABLE currencies (
    code VARCHAR(3) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE deals (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    amount_sold DECIMAL(19, 4) NOT NULL,
    amount_bought DECIMAL(19, 4) NOT NULL,
    exchange_rate DECIMAL(19, 8) NOT NULL,
    pairs_short_name VARCHAR(10) NOT NULL,
    interface_date DATETIME NOT NULL,
    from_currency_code VARCHAR(3) NOT NULL,
    to_currency_code VARCHAR(3) NOT NULL,
    FOREIGN KEY (from_currency_code) REFERENCES currencies(code),
    FOREIGN KEY (to_currency_code) REFERENCES currencies(code)
);