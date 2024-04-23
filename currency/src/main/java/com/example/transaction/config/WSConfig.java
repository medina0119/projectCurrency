package com.example.transaction.config;

import com.example.transaction.repository.LimitRepository;
import com.example.transaction.repository.TransactionRepository;
import com.example.transaction.service.impl.TransactionServiceImpl;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WSConfig {
    @Autowired
    private Bus bus;

    private final TransactionRepository transactionRepository;
    private final LimitRepository limitRepository;

    public WSConfig(TransactionRepository transactionRepository, LimitRepository limitRepository) {
        this.transactionRepository = transactionRepository;
        this.limitRepository = limitRepository;
    }

    @Bean
    public Endpoint getEndpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, new TransactionServiceImpl(transactionRepository, limitRepository));
        endpoint.publish("/ServiceOperation");
        return endpoint;
    }

}
