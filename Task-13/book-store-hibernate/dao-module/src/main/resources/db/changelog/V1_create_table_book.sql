--changeset balabol:1
        CREATE TABLE IF NOT EXISTS book (
        bookId SERIAL PRIMARY KEY,
        nameBook VARCHAR(40),
        authorBook VARCHAR(40),
        datePublication DATE,
        dateAddWarehouse DATE,
        price NUMERIC(6,2),
        availability BOOLEAN
        )
