package com.statics.transactionstatistics.integrantionTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TransactionsStatisticsIntegrationTests {

    private static final String transactionEndpoint = "http://localhost:8080/transactions";
    private static final String statisticsEndpoint = "http://localhost:8080/statistics";

    @Autowired
    private TestRestTemplate testRestTemplate;

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    public void shouldReturn204WhenInsertNewTransactionWith60Seconds() {
        String payload = "{\"amount\": \"12.3343\",\"timestamp\": \"2018-07-17T09:59:51.312Z\"}";
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(transactionEndpoint, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void shouldReturn201WhenInsertNewTransactionWithLess60Seconds() throws InterruptedException {
        Date date = new Date();
        TimeUnit.SECONDS.sleep(5);
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String payload = "{\"amount\": \"12.3343\",\"timestamp\": \"" + outputFormat.format(date) + "\"}";
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(transactionEndpoint, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void shouldReturn400WhenInsertNewTransactionWhenJsonIsInvalid() {
        String payload = "\"amount\": \"12.3343\"\"timestamp\": \"2018-07-17T09:59:51.312Z\"}";
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(transactionEndpoint, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturn422WhenInsertNewTransactionWithFutureDate() {
        String payload = "{\"amount\": \"12.3343\",\"timestamp\": \"2099-07-17T09:59:51.312Z\"}";
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(transactionEndpoint, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void shouldReturn422WhenDeleteTransactions() {
        HttpEntity<String> entity = new HttpEntity<String>(buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(transactionEndpoint, HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void shouldReturn200WhenGetStatisticReport() {
        HttpEntity<String> entity = new HttpEntity<String>(buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(statisticsEndpoint, HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
