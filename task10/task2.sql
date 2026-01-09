--1. Найти номер модели, скорость и размер жесткого диска для всех ПК стоимостью менее 500 долларов.
SELECT code, model, speed, hd FROM PC
WHERE price < 500::money;


--2. Найти производителей принтеров. Вывести поля: maker.
SELECT DISTINCT maker FROM Product
WHERE type = 'Printer';


--3. Найти номер модели, объем памяти и размеры экранов ноутбуков, цена которых превышает 1000 долларов.
SELECT code, ram, screen FROM Laptop
WHERE price > 1000::money;


--4. Найти все записи таблицы Printer для цветных принтеров.
SELECT * FROM Printer
WHERE color = 'y';


--5. Найти номер модели, скорость и размер жесткого диска для ПК, имеющих скорость cd 12x или 24x и цену менее 600 долларов.
SELECT model, speed, hd FROM PC
WHERE (cd = '12x' OR cd = '24x') AND price < 600::money;


--6. Указать производителя и скорость для тех ноутбуков, которые имеют жесткий диск объемом не менее 100 Гбайт.
SELECT maker, speed FROM Product AS p
JOIN Laptop AS l ON l.model = p.model
WHERE hd >= 100;


--7. Найти номера моделей и цены всех продуктов (любого типа), выпущенных производителем B (латинская буква).
SELECT p.model, pc.price FROM Product AS p
JOIN PC AS pc ON pc.model = p.model
WHERE maker = 'B'
UNION
SELECT p.model, pr.price FROM Product AS p
JOIN Printer AS pr ON pr.model = p.model
WHERE maker = 'B'
UNION
SELECT p.model, l.price FROM Product AS p
JOIN Laptop AS l ON l.model = p.model
WHERE maker = 'B'


--8. Найти производителя, выпускающего ПК, но не ноутбуки.
SELECT DISTINCT maker FROM Product
WHERE type = 'PC' AND maker NOT IN (SELECT maker FROM Product WHERE type = 'Laptop')


--9. Найти производителей ПК с процессором не менее 450 Мгц. Вывести поля: maker.
SELECT DISTINCT maker FROM PC AS pc
JOIN Product AS p ON p.model = pc.model
WHERE speed >= 450;


--10. Найти принтеры, имеющие самую высокую цену. Вывести поля: model, price.
SELECT model, price FROM Printer
WHERE price = (SELECT MAX(price) FROM Printer)


--11. Найти среднюю скорость ПК.
SELECT AVG(speed) FROM PC


--12. Найти среднюю скорость ноутбуков, цена которых превышает 1000 долларов.
SELECT AVG(speed) FROM Laptop
WHERE price > 1000::money;


--13. Найти среднюю скорость ПК, выпущенных производителем A.
SELECT AVG(speed) FROM Product AS p
JOIN PC AS pc ON pc.model = p.model
WHERE p.maker = 'A';


--14. Для каждого значения скорости процессора найти среднюю стоимость ПК с такой же скоростью. Вывести поля: скорость, средняя цена.
SELECT speed, SUM(price)/COUNT(price) AS avg_price FROM PC
GROUP BY speed


--15. Найти размеры жестких дисков, совпадающих у двух и более PC. Вывести поля: hd.
SELECT hd FROM PC
GROUP BY hd
HAVING COUNT(hd) >= 2


--16. Найти пары моделей PC, имеющих одинаковые скорость процессора и RAM. В результате каждая пара указывается только один раз, т.е. (i,j), но не (j,i), 
--Порядок вывода полей: модель с большим номером, модель с меньшим номером, скорость, RAM.
SELECT p1.model AS model_high, p2.model AS model_low, p1.speed, p1.ram FROM PC AS p1
JOIN PC AS p2 ON p1.speed = p2.speed AND p1.ram = p2.ram AND p1.model > p2.model
ORDER BY p1.model DESC, p2.model DESC;


--17. Найти модели ноутбуков, скорость которых меньше скорости любого из ПК. Вывести поля: type, model, speed.
SELECT type, l.model, speed FROM Laptop AS l
JOIN Product AS p ON p.model = l.model
WHERE speed < (SELECT MIN(speed) FROM PC)


--18. Найти производителей самых дешевых цветных принтеров. Вывести поля: maker, price.
SELECT DISTINCT maker, price FROM Product AS p
JOIN Printer AS pr ON p.model = pr.model
WHERE price = (SELECT MIN(price) FROM Printer WHERE color = 'y') AND pr.color = 'y';


--19. Для каждого производителя найти средний размер экрана выпускаемых им ноутбуков. 
--Вывести поля: maker, средний размер экрана.
SELECT maker, AVG(screen) FROM Product AS p
JOIN Laptop AS l ON l.model = p.model
GROUP BY maker


--20. Найти производителей, выпускающих по меньшей мере три различных модели ПК. 
--Вывести поля: maker, число моделей.
SELECT maker FROM Product
WHERE type = 'PC'
GROUP BY maker
HAVING COUNT(type) >= 3


--21. Найти максимальную цену ПК, выпускаемых каждым производителем. Вывести поля: maker, максимальная цена.
SELECT maker, MAX(price) FROM Product AS p
JOIN PC AS pc ON pc.model = p.model
GROUP BY maker


--22. Для каждого значения скорости процессора ПК, превышающего 600 МГц, найти среднюю цену ПК с такой же скоростью. 
--Вывести поля: speed, средняя цена.
SELECT speed, SUM(price)/COUNT(price) AS avg_price FROM PC
WHERE speed > 600
GROUP BY speed


--23. Найти производителей, которые производили бы как ПК, так и ноутбуки со скоростью не менее 750 МГц. 
--Вывести поля: maker
SELECT DISTINCT maker FROM Product
WHERE maker IN (
	SELECT DISTINCT maker FROM Product AS p
	JOIN Laptop AS l ON l.model = p.model
	WHERE speed >= 750) 
AND maker IN (
	SELECT DISTINCT maker FROM Product AS p
	JOIN PC AS pc ON pc.model = p.model
	WHERE speed >= 750)


--24.Перечислить номера моделей любых типов, имеющих самую высокую цену по всей имеющейся в базе данных продукции.
WITH MaxPrice AS (
    SELECT MAX(price) AS max_price
    FROM (
        SELECT price FROM PC
        UNION ALL
        SELECT price FROM Laptop
        UNION ALL
        SELECT price FROM Printer
    ) AS all_prices
)
SELECT model, 'PC' AS type, price
FROM PC
WHERE price = (SELECT max_price FROM MaxPrice)
UNION ALL
SELECT model, 'Laptop' AS type, price
FROM Laptop
WHERE price = (SELECT max_price FROM MaxPrice)
UNION ALL
SELECT model, 'Printer' AS type, price
FROM Printer
WHERE price = (SELECT max_price FROM MaxPrice);


--25. Найти производителей принтеров, которые производят ПК с наименьшим объемом RAM и с самым быстрым процессором среди всех ПК, 
--имеющих наименьший объем RAM. Вывести поля: maker
WITH MinRAM AS (
    SELECT MIN(ram) AS min_ram
    FROM PC
),
FastestPC AS (
    SELECT pc.model, p.maker
    FROM PC AS pc
    JOIN Product AS p ON p.model = pc.model
    WHERE pc.ram = (SELECT min_ram FROM MinRAM)
      AND pc.speed = (
          SELECT MAX(speed)
          FROM PC
          WHERE ram = (SELECT min_ram FROM MinRAM)
      )
)
SELECT DISTINCT pr_product.maker
FROM Product AS pr_product
JOIN Printer AS pr ON pr.model = pr_product.model
WHERE pr_product.maker IN (
    SELECT maker
    FROM FastestPC
);



