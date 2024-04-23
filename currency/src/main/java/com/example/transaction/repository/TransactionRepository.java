package com.example.transaction.repository;

import com.example.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(
            "SELECT t FROM Transaction t WHERE t.accountFrom = :accountFrom AND t.accountTo = :accountTo " +
            "AND t.expenseCategory = :expenseCategory"
    )
    List<Transaction> getClientsTransactionList(@RequestParam String accountFrom, String accountTo, String expenseCategory);

    @Query(value = """
SELECT
    date,
    limit_sum,
    remaining,
    sum,
    CASE
        WHEN remaining >= 0 THEN false
        WHEN remaining < 0 THEN true
    END AS limit_exceeded
FROM (
    SELECT
        date,
        limit_sum,
        (
            (SELECT COALESCE(MAX(d.limit_sum), 0) FROM dev.limit d WHERE d.limit_datetime <= t.date) -
            (SELECT COALESCE(SUM(d.sum), 0) FROM dev.transaction d WHERE d.transaction_date <= t.date)
        ) AS remaining,
        sum
    FROM (
        SELECT
            account_from,
            account_to,
            sum,
            currency_shortname,
            transaction_date AS date,
            expense_category,
            NULL AS limit_sum,
            NULL AS limit_currency_shortname
        FROM
            dev.transaction
        UNION ALL
        SELECT
            account_from,
            account_to,
            NULL AS sum,
            NULL AS currency_shortname,
            limit_datetime AS date,
            :expenseCategory AS expense_category,
            limit_sum,
            limit_currency_shortname
        FROM dev.limit
    ) AS t
    WHERE t.account_from = :accountFrom AND t.account_to = :accountTo AND t.expense_category = :expenseCategory
    ORDER BY t.date
) AS tt
""", nativeQuery = true)
    List<Object[]> getLimitTransaction(@RequestParam String accountFrom, String accountTo, String expenseCategory);

    @Query(value = """
SELECT * FROM (SELECT
    date,
    limit_sum,
    remaining,
    sum,
    CASE
        WHEN remaining >= 0 THEN false
        WHEN remaining < 0 THEN true
    END AS limit_exceeded
FROM (
    SELECT
        date,
        limit_sum,
        (
            (SELECT COALESCE(MAX(d.limit_sum), 0) FROM dev.limit d WHERE d.limit_datetime <= t.date) -
            (SELECT COALESCE(SUM(d.sum), 0) FROM dev.transaction d WHERE d.transaction_date <= t.date)
        ) AS remaining,
        sum
    FROM (
        SELECT
            account_from,
            account_to,
            sum,
            currency_shortname,
            transaction_date AS date,
            expense_category,
            NULL AS limit_sum,
            NULL AS limit_currency_shortname
        FROM
            dev.transaction
        UNION ALL
        SELECT
            account_from,
            account_to,
            NULL AS sum,
            NULL AS currency_shortname,
            limit_datetime AS date,
            :expenseCategory AS expense_category,
            limit_sum,
            limit_currency_shortname
        FROM dev.limit
    ) AS t
    WHERE t.account_from = :accountFrom AND t.account_to = :accountTo AND t.expense_category = :expenseCategory
    ORDER BY t.date
) AS tt ) AS LIM
WHERE lim.limit_exceeded is true
""", nativeQuery = true)
    List<Object[]> getExceededLimit(@RequestParam String accountFrom, String accountTo, String expenseCategory);
}

