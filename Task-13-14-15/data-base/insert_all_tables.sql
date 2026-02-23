--Вставка данных в таблицу книга
INSERT INTO book (nameBook, authorBook,datePublication,dateAddWarehouse,price,availability)
VALUES('Le Petit Prince','Antoine de Saint-Exupery','1943-04-06','2025-03-15',200,true),
('Roadside Picnic','Arkady Strugatsky и Boris Strugatsky','1971-07-02','2026-01-15',400,true),
('Metro 2033','Dmitry Glukhovsky','2005-01-23','2025-11-11',800,true),
('It','Stephen King','1986-02-02','2025-08-18',500,true),
('Brave New World','Aldous Huxley','1932-12-01','2025-08-18',900,true);

--Вставка данных в таблицу заказ
INSERT INTO order_ (dateComplection,bookId,emailUser,statusOrder)
VALUES('2026-02-14',3,'dima1234@gmail.com','NEW'),
('2025-12-14',1,'nikita456@gmail.com','CLOSED'),
('2026-01-14',2,'maks@mail.com','NEW');

--Вставка данных в таблицу запрос
INSERT INTO request (bookId,countRequest,isClosed)
VALUES(4,3,true),
(5,1,true);

