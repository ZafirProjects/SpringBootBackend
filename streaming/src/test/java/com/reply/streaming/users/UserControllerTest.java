package com.reply.streaming.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testRegisterValidUser() {
        Users validUser = new Users("User1","Password1", "email1@address1.com", "2001-11-20", "4242424242424242");

        when(userService.basicValidation(any())).thenReturn(true);
        when(userService.isOfAge(any())).thenReturn(true);
        when(userService.availableUsername(any())).thenReturn(true);

        ResponseEntity<String> response = userController.register(validUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).addUser(validUser);
    }

    @Test
    public void testRegisterInvalidRequest() {
        Users invalidUser = new Users("User1","Pass", "email1@address1.com", "2001-11-20", "4242424242424242");

        when(userService.basicValidation(any())).thenReturn(false);

        ResponseEntity<String> response = userController.register(invalidUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Verify that addUser is not invoked for an invalid request
        verify(userService, never()).addUser(invalidUser);
    }

    @Test
    public void testRegisterUnderAgeRequest() {
        Users invalidUser = new Users("User1","Pass", "email1@address1.com", "2010-11-20", "4242424242424242");

        when(userService.basicValidation(any())).thenReturn(true);
        when(userService.isOfAge(any())).thenReturn(false);

        ResponseEntity<String> response = userController.register(invalidUser);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        // Verify that addUser is not invoked for an invalid request
        verify(userService, never()).addUser(invalidUser);
    }

    @Test
    public void testRegisterInUseUsernameRequest() {
        Users invalidUser = new Users("User1","Pass", "email1@address1.com", "2001-11-20", "4242424242424242");

        when(userService.basicValidation(any())).thenReturn(true);
        when(userService.isOfAge(any())).thenReturn(true);
        when(userService.availableUsername(any())).thenReturn(false);

        ResponseEntity<String> response = userController.register(invalidUser);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        // Verify that addUser is not invoked for an invalid request
        verify(userService, never()).addUser(invalidUser);
    }

    @Test
    public void testGetUsersWithCreditCard() {
        List<Users> users = List.of(
                new Users("User1", "Password1", "email1@address1.com", "2001-11-20", "4242424242424242")
        );
        // Mock the service to return users with credit card numbers
        when(userService.getUsersWithCreditCardNumber()).thenReturn(users);

        List<Users> returnedUsers = userController.getUsers("Yes");

        verify(userService, times(1)).getUsersWithCreditCardNumber();
        Assertions.assertEquals(users, returnedUsers);
    }

    @Test
    public void testGetUsersWithoutCreditCard() {
        // Mock the service to return users with credit card numbers
        List<Users> users = List.of(
                new Users("User2", "Password2", "email2@address1.com", "2001-11-20", "")
        );
        when(userService.getUsersWithoutCreditCardNumber()).thenReturn(users);

        List<Users> returnedUsers = userController.getUsers("No");

        Assertions.assertEquals(users, returnedUsers);
        verify(userService, times(1)).getUsersWithoutCreditCardNumber();
    }
}
