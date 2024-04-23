package com.example.currency.service.impl;

import com.example.currency.model.Bar;
import com.example.currency.repository.CurrencyRepository;
import com.example.currency.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import java.net.HttpURLConnection;
import java.net.URL;


@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private static final String REQUEST_URL = "https://api.twelvedata.com/time_series?symbol=AAPL&interval=1min&outputsize=12&apikey=af7360f7df4b4767bb294780b3b85d6c";
    private static final String QUOTE_URL = "https://api.twelvedata.com/quote?symbol=RUB/USD&apikey=af7360f7df4b4767bb294780b3b85d6c";

    private final CurrencyRepository currencyRepository;

    @Override
    @Scheduled(cron = "0 0 8 * * *")    // Запускать функционал каждый день
    //@Scheduled(fixedRate = 60000)
    public void downloadCurrentBar() {
        try {
            URL requestURL = new URL(QUOTE_URL);
            HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
            StringBuffer responseData = new StringBuffer();
            JSONParser parser = new JSONParser();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "twelve_java/1.0");
            connection.connect();

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Request failed. Error: " + connection.getResponseMessage());
            }

            Scanner scanner = new Scanner(requestURL.openStream());
            while (scanner.hasNextLine()) {
                responseData.append(scanner.nextLine());
            }

            JSONObject json = (JSONObject) parser.parse(responseData.toString());
            String symbol = (String) json.get("symbol");
            String name = (String) json.get("name");
            String exchange = (String) json.get("exchange");
            Date datetime = new SimpleDateFormat("yyyy-MM-dd").parse((String) json.get("datetime"));
            BigDecimal open = new BigDecimal((String) json.get("open"));
            BigDecimal high = new BigDecimal((String) json.get("high"));
            BigDecimal low = new BigDecimal((String) json.get("low"));
            BigDecimal close = new BigDecimal((String) json.get("close"));
            BigDecimal previous_close = new BigDecimal((String) json.get("previous_close"));
            BigDecimal change = new BigDecimal((String) json.get("change"));
            BigDecimal percent_change = new BigDecimal((String) json.get("percent_change"));
            boolean is_market_open = (boolean) json.get("is_market_open");

            Bar bar = new Bar(symbol, name, exchange, datetime, open, high, low, close, previous_close, change, percent_change, is_market_open);
            currencyRepository.save(bar);

            connection.disconnect();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }
}
