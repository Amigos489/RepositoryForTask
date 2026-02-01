--Создание таблицы запрос
CREATE TABLE IF NOT EXISTS request (
	requestId SERIAL PRIMARY KEY,
	bookId INTEGER,
	FOREIGN KEY (bookId) REFERENCES book (bookId),
	countRequest SMALLINT,
	isClosed BOOLEAN
)