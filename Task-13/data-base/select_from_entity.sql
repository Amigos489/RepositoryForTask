--получить название книги через запрос
SELECT r.requestId, r.bookId, b.nameBook FROM request AS r
JOIN book AS b ON b.bookId = r.bookId;

--получить цену книги через книгу
SELECT o.orderId, o.bookId, b.price FROM order_ AS o
JOIN book AS b ON b.bookId = o.bookId;
