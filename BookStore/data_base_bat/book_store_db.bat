@echo off
REM Настройки подключения к БД
SET PGPASSWORD=balabol
SET DB_USER=postgres
SET DB_NAME=book-store
SET DB_HOST=localhost
SET DB_PORT=5432

psql -U %DB_USER% -h %DB_HOST% -p %DB_PORT% -d %DB_NAME% -f CREATE_TABLE_Books.sql
psql -U %DB_USER% -h %DB_HOST% -p %DB_PORT% -d %DB_NAME% -f CREATE_TABLE_Orders.sql
psql -U %DB_USER% -h %DB_HOST% -p %DB_PORT% -d %DB_NAME% -f CREATE_TABLE_Requests.sql
psql -U %DB_USER% -h %DB_HOST% -p %DB_PORT% -d %DB_NAME% -f INSERT_INTO_Books.sql
psql -U %DB_USER% -h %DB_HOST% -p %DB_PORT% -d %DB_NAME% -f INSERT_INTO_Orders.sql
psql -U %DB_USER% -h %DB_HOST% -p %DB_PORT% -d %DB_NAME% -f INSERT_INTO_Requests.sql

echo "База данных успешно создана и заполнена тестовыми данными."
pause