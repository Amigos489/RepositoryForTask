--Заказы
CREATE TABLE Orders (
	orderID SERIAL PRIMARY KEY,
	customerEmail VARCHAR(40),
	orderStatus OrderStatus,
	dateOfExecution DATE,
	bookID INT,
	FOREIGN KEY (bookID) REFERENCES books (bookID)
)

-- DROP TABLE Orders;