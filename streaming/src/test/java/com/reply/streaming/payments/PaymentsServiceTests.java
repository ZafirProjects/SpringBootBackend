package com.reply.streaming.payments;

import com.reply.streaming.users.UserRepository;
import com.reply.streaming.users.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class PaymentsServiceTests {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void PaymentService_ValidateNumber_ReturnsBoolean() {
        // Arrange
        Payment payment = new Payment("4242424242424242", 100);

        // Act
        boolean payment_response = paymentService.validateCCNumber(payment.getCreditCardNumber());

        // Assert
        Assertions.assertTrue(payment_response);
    }

    @Test
    public void PaymentService_ValidateAmount_ReturnsBoolean() {
        // Arrange
        Payment payment = new Payment("4242424242424242", 100);

        // Act
        boolean payment_response = paymentService.validateAmount(payment.getAmount());

        // Assert
        Assertions.assertTrue(payment_response);
    }

    @Test
    public void PaymentService_isRegisteredCard_ReturnsBoolean() {
        // Arrange
        List<Users> users = Arrays.asList(
                new Users("User1","Password1", "email1@address.com", "2001-11-20", "4242424242424242"),
                new Users("User2","Password2", "email2@address.com", "2000-01-13", "")
        );
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Payment payment = new Payment("4242424242424242", 100);

        // Act
        boolean payment_response = paymentService.registeredCardNumber(payment.getCreditCardNumber());

        // Assert
        Assertions.assertTrue(payment_response);
    }
}
