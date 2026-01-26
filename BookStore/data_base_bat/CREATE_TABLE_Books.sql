--Книги
CREATE TABLE Books (
	bookID SERIAL PRIMARY KEY,
	nameBook VARCHAR(40),
	authorBook VARCHAR(40),
	dateOfPublication DATE,
	dateAddedToWarehouse DATE,
	numberOfCopies SMALLINT,
	numberOfPages SMALLINT,
	numberOfRequests SMALLINT,
	price MONEY,
	availability BOOLEAN
)

-- DROP TABLE Books;