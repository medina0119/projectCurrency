package com.example.transaction.controller;

import com.example.transaction.model.Transaction;
import com.example.transaction.repository.LimitRepository;
import com.example.transaction.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/main")
    public String getTest() {
       return "Hello TransactionController";
    }

    @GetMapping("/transactions")    // Вывод всех транзакций
    public String getTransactions() throws JsonProcessingException {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        String jsonData = objectMapper.writeValueAsString(transactions);
        return jsonData;
    }

    @PostMapping("/clientsTrasactions")     // Вывод всех транзакций клиента
    public String getClientTransactions(@RequestParam String accountFrom, String accountTo, String expenseCategory) throws JsonProcessingException {
        Iterable<Transaction> transactions = transactionRepository.getClientsTransactionList(accountFrom, accountTo, expenseCategory);
        String jsonData = objectMapper.writeValueAsString(transactions);
        return jsonData;
    }

    @PostMapping("/limitedTrasactions")     // Выставление флагов транзакций
    public String getLimitTransaction(@RequestParam String accountFrom, String accountTo, String expenseCategory) throws JsonProcessingException {
        List<Object[]> transactions = transactionRepository.getLimitTransaction(accountFrom, accountTo, expenseCategory);
        String jsonData = objectMapper.writeValueAsString(transactions);
        return jsonData;
    }

    @PostMapping("/exceededLimits")     // Транзакции превысившие лимит
    public String getExceededLimit(@RequestParam String accountFrom, String accountTo, String expenseCategory) throws JsonProcessingException {
        List<Object[]> transactions = transactionRepository.getLimitTransaction(accountFrom, accountTo, expenseCategory);
        String jsonData = objectMapper.writeValueAsString(transactions);
        return jsonData;
    }

}
