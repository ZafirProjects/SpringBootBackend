package com.reply.streaming.payments;

import com.reply.streaming.users.UserController;
import com.reply.streaming.users.UserService;
import com.reply.streaming.users.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PaymentControllerTests {
    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMakeValidPayment() {
        Payment validPayment = new Payment("4242424242424242", 100);

        when(paymentService.validateCCNumber(any())).thenReturn(true);
        when(paymentService.validateAmount(anyInt())).thenReturn(true);
        when(paymentService.registeredCardNumber(any())).thenReturn(true);

        ResponseEntity<String> response = paymentController.makePayment(validPayment);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testMakeInvalidPayment() {
        Payment invalidPayment = new Payment("4242424242424242", 1000);
        when(paymentService.validateCCNumber(any())).thenReturn(true);
        when(paymentService.validateAmount(anyInt())).thenReturn(false);
        when(paymentService.registeredCardNumber(any())).thenReturn(true);

        ResponseEntity<String> response = paymentController.makePayment(invalidPayment);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testMakevalidPaymentWithoutRegisteredCard() {
        Payment invalidPayment = new Payment("4242424242424242", 1000);
        when(paymentService.validateCCNumber(any())).thenReturn(true);
        when(paymentService.validateAmount(anyInt())).thenReturn(true);
        when(paymentService.registeredCardNumber(any())).thenReturn(false);

        ResponseEntity<String> response = paymentController.makePayment(invalidPayment);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
