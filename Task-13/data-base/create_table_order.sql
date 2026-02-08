--Создание таблицы заказ
CREATE TABLE IF NOT EXISTS order_ (
	orderId SERIAL PRIMARY KEY,
	dateComplection DATE,
	bookId INTEGER,
	FOREIGN KEY (bookId) REFERENCES book (bookId),
	emailUser VARCHAR(40),
	statusOrder VARCHAR(10)
)