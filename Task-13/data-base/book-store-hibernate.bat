@echo off

set user=postgres
set password=balabol
set host=localhost
set /a port=5432
set dbname=book-store-hibernate

rem Создание таблиц
psql -f create_table_book.sql postgresql://%user%:%password%@%host%:%port%/%dbname%
psql -f create_table_order.sql postgresql://%user%:%password%@%host%:%port%/%dbname%
psql -f create_table_request.sql postgresql://%user%:%password%@%host%:%port%/%dbname%

rem Заполнение таблиц данными
psql -f insert_all_tables.sql postgresql://%user%:%password%@%host%:%port%/%dbname%

rem Удаление таблиц (убрать rem в команде ниже)
rem psql -f drop_all_tables.sql postgresql://%user%:%password%@%host%:%port%/%dbname%

echo database done!
pause