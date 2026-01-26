--Запросы на книгу
CREATE TABLE Requests (
	requestID SERIAL PRIMARY KEY,
	bookID INT,
	FOREIGN KEY (bookID) REFERENCES books (bookID),
	requestCount SMALLINT,
	fulfilled BOOLEAN DEFAULT FALSE
)

-- DROP TABLE Requests;