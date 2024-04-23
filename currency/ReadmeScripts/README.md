Описание микросервиса:

1. Для приема транзакций используется SOAP API
   http://localhost:8081/Service
   http://localhost:8081/Service/ServiceOperation?wsdl
    Модель: Transaction
   (Прим. При входе параметра operationType = TRANSACTION)
Скрипт по созданию таблицы из PostgreSQL
-- Table: dev.transaction
-- DROP TABLE IF EXISTS dev.transaction;
CREATE TABLE IF NOT EXISTS dev.transaction
(
id bigint NOT NULL,
account_from character varying(255) COLLATE pg_catalog."default",
account_to character varying(255) COLLATE pg_catalog."default",
sum numeric(38,2),
currency_shortname character varying(255) COLLATE pg_catalog."default",
expense_category character varying(255) COLLATE pg_catalog."default",
transaction_date timestamp(6) without time zone,
CONSTRAINT transaction_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS dev.transaction
OWNER to postgres;

2. Для приема месячных лимитов используется SOAP API
   http://localhost:8081/Service
   http://localhost:8081/Service/ServiceOperation?wsdl
    Модель: Limit
   (Прим. При входе параметра operationType = LIMIT)
Скрипт по созданию таблицы из PostgreSQL
-- Table: dev.limit
-- DROP TABLE IF EXISTS dev."limit";
CREATE TABLE IF NOT EXISTS dev."limit"
(
id bigint NOT NULL,
account_from character varying(255) COLLATE pg_catalog."default",
account_to character varying(255) COLLATE pg_catalog."default",
limit_currency_shortname character varying(255) COLLATE pg_catalog."default",
limit_datetime timestamp(6) without time zone,
limit_sum numeric(38,2),
CONSTRAINT limit_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS dev."limit"
OWNER to postgres;

3. Логика запроса биржевых курсов валютных пар - CurrencyServiceImpl
    Модель: Bar
Отработка - ежедневно в 8 утра, с сайта twelvedata.com

4. Внешние запросы от клиентов выполнены через REST API
4.1. /transactions           // Вывод всех транзакций
4.2. /clientsTrasactions     // Вывод всех транзакций клиента (фильтр по accountFrom, accountTo, expenseCategory)
4.3. /limitedTrasactions     // Выставление флагов транзакций, расчет остатка месячного лимита, limit-exceeded (фильтр по accountFrom, accountTo, expenseCategory)
4.4. /exceededLimits         // Транзакции превысившие лимит