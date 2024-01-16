package com.reply.streaming.payments;

import com.reply.streaming.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity<String> makePayment(@RequestBody Payment paymentRequest) {
        if (!paymentService.validateCCNumber(paymentRequest.getCreditCardNumber()) || !paymentService.validateAmount(paymentRequest.getAmount())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!paymentService.registeredCardNumber(paymentRequest.getCreditCardNumber())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
