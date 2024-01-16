package com.reply.streaming.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public List<Users> getUsersWithCreditCardNumber() {
        List<Users> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users.stream().filter(user -> !user.getCreditCardNumber().equals("")).toList();
    }

    public List<Users> getUsersWithoutCreditCardNumber() {
        List<Users> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users.stream().filter(user -> user.getCreditCardNumber().equals("")).toList();
    }

    public boolean basicValidation(Users registerRequest) {
        return isAlphanumeric(registerRequest.getUsername()) && isValidPassword(registerRequest.getPassword()) && isValidEmail(registerRequest.getEmail()) && isValidDoB(registerRequest.getDoB()) && isValidCCNumber(registerRequest.getCreditCardNumber());
    }

    public boolean availableUsername(String usernameRequest) {
        List<Users> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        for (Users user : users) {
            if (user.getUsername().equals(usernameRequest)) {
                return false;
            }
        }
        return true;
    }

    public boolean isOfAge(String dateOfBirth) {
        int age = Period.between(LocalDate.parse(dateOfBirth), LocalDate.now()).getYears();
        return age >= 18;
    }

    public void addUser(Users registerRequest) {
        userRepository.save(registerRequest);
    }

    private boolean isValidCCNumber(String creditCardNumber) {
        for (char ch: creditCardNumber.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return creditCardNumber.equals("") || creditCardNumber.length() == 16;
    }

    private boolean isValidDoB(String doB) {
        try {
            LocalDate.parse(doB);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && hasUpperCaseAndNumber(password);
    }

    private boolean hasUpperCaseAndNumber(String password) {
        boolean hasUpper = false;
        boolean hasNumber = false;
        for (char ch: password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            }
        }
        return hasUpper && hasNumber;
    }

    private boolean isAlphanumeric(String input) {
        String regex = "^[a-zA-Z0-9]+$";

        return input.matches(regex);
    }
}
