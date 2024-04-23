package com.example.transaction.service.impl;

import com.example.transaction.OperationType;
import com.example.transaction.model.Limit;
import com.example.transaction.model.Transaction;
import com.example.transaction.repository.LimitRepository;
import com.example.transaction.repository.TransactionRepository;
import com.example.transaction.service.TransactionService;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;

@WebService(
        serviceName = "TransactionService",
        portName = "TransactionPort",
        targetNamespace = "http://service.ws.transaction/",
        endpointInterface = "com.example.transaction.service.TransactionService"
)
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final LimitRepository limitRepository;

    @Override
    public String insertOperation(OperationType operationType, String accountFrom, String accountTo, String currencyCode, BigDecimal amount, String expenseCategory, BigDecimal limitSum, String currencyLimit) {

        try {
            if (operationType == OperationType.TRANSACTION) {
                Transaction transaction = new Transaction(accountFrom, accountTo, currencyCode, amount, expenseCategory, Calendar.getInstance().getTime());
                transactionRepository.save(transaction);
                return "Transaction saved!";
            }
            else if (operationType == OperationType.LIMIT) {
                Limit limit = new Limit(accountFrom, accountTo, limitSum, Calendar.getInstance().getTime(), currencyLimit);
                limitRepository.save(limit);
                return "Limit saved!";
            }
            return "";
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
            return String.valueOf(e);
        }
    }

}
