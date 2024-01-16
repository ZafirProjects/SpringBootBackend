package com.reply.streaming.payments;

import com.reply.streaming.users.Users;
import com.reply.streaming.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private UserRepository userRepository;

    public boolean validateCCNumber(String creditCardNumber) {
        for (char ch: creditCardNumber.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return creditCardNumber.equals("") || creditCardNumber.length() == 16;
    }

    public boolean validateAmount(int amount) {
        int length = String.valueOf(amount).length();
        return length == 3;
    }

    public boolean registeredCardNumber(String creditCardNumber) {
        for (Users user : userRepository.findAll()) {
            if (user.getCreditCardNumber().equals(creditCardNumber)) {
                return true;
            }
        }
        return false;
    }
}
