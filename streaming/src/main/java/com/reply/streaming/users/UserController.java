package com.reply.streaming.users;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<String> register(@RequestBody Users registerRequest){
        if (!userService.basicValidation(registerRequest)) {
            return new ResponseEntity<>("The request did not meet basic validation checks", HttpStatus.BAD_REQUEST);
        }
        if (!userService.isOfAge(registerRequest.getDoB())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (!userService.availableUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.addUser(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public List<Users> getUsers(@RequestParam(name = "CreditCard", required = false) String withCreditCard){
        if (withCreditCard.equals("No")) {
            return userService.getUsersWithoutCreditCardNumber();
        }else if (withCreditCard.equals("Yes")) {
            return userService.getUsersWithCreditCardNumber();
        }else {
            return userService.getUsersWithCreditCardNumber();
        }
    }
}
