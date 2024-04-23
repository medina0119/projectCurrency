package com.example.transaction.service;

import com.example.transaction.OperationType;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;
import java.math.BigDecimal;
import java.util.Date;

@WebService(targetNamespace = "http://service.ws.transaction/", name = "TransactionService")
public interface TransactionService {

    @WebResult(targetNamespace = "", name = "returnOperation")
    @RequestWrapper(
            localName = "insertOperation",
            targetNamespace = "http://service.ws.transaction/",
            className = "com.example.transaction.service.OperationRequest"
    )
    @WebMethod(action = "urn:insertOperation")
    @ResponseWrapper(
            localName = "insertOperationResponse",
            targetNamespace = "http://service.ws.transaction/",
            className = "com.example.transaction.service.OperationResponse"
    )
    String insertOperation(
            @WebParam(name = "operationType", targetNamespace = "") OperationType operationType,
            @WebParam(name = "accountFrom", targetNamespace = "") String accountFrom,
            @WebParam(name = "accountTo", targetNamespace = "") String accountTo,
            @WebParam(name = "currencyCode", targetNamespace = "") String currencyCode,
            @WebParam(name = "amount", targetNamespace = "") BigDecimal amount,
            @WebParam(name = "expenseCategory", targetNamespace = "") String expenseCategory,
            @WebParam(name = "limitSum", targetNamespace = "") BigDecimal limitSum,
            @WebParam(name = "currencyLimit", targetNamespace = "") String currencyLimit
            );

}
