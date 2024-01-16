package com.reply.streaming.users;

import org.junit.Assert;
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
class UserServicesTests {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_GetUsersWithCard_ReturnsUsersWithRegisteredCard() {
        // Arrange
        List<Users> users = Arrays.asList(
                new Users("User1","Password1", "email1@address.com", "2001-11-20", "4242424242424242"),
                new Users("User2","Password2", "email2@address.com", "2000-01-13", "")
        );
        Mockito.when(userRepository.findAll()).thenReturn(users);
        // Act
        List<Users> returnedUsers = userService.getUsersWithCreditCardNumber();

        // Assert
        for (Users user : returnedUsers) {
            Assert.assertNotEquals("", user.getCreditCardNumber());
        }
    }

    @Test
    public void UserService_GetUsersWithoutCard_ReturnsUsersWithNoRegisteredCard() {
        // Arrange
        List<Users> users = Arrays.asList(
                new Users("User1","Password1", "email1@address.com", "2001-11-20", "4242424242424242"),
                new Users("User2","Password2", "email2@address.com", "2000-01-13", "")
        );
        Mockito.when(userRepository.findAll()).thenReturn(users);

        // Act
        List<Users> returnedUsers = userService.getUsersWithoutCreditCardNumber();

        // Assert
        for (Users user : returnedUsers) {
            Assert.assertEquals(user.getCreditCardNumber(), "");
        }

    }

    @Test
    public void UserService_RequestValidation_ReturnBoolean() {
        // Arrange
        Users user1 = new Users("User1","Password1", "email1@address.com", "2001-11-20", "4242424242424242");
        Users user2 = new Users("User2","Password2", "email2@address.com", "200001-13", "");
        Users user3 = new Users("User3","Pass", "email3@address.com", "2000-01-13", "");
        Users user4 = new Users("User4","Password4", "email4@address.com", "2001-11-20", "424242424242424");
        Users user5 = new Users("User5","Password5", "email5address.com", "2001-11-20", "4242424242424242");

        // Act
        boolean user1_response = userService.basicValidation(user1);
        boolean user2_response = userService.basicValidation(user2);
        boolean user3_response = userService.basicValidation(user3);
        boolean user4_response = userService.basicValidation(user4);
        boolean user5_response = userService.basicValidation(user5);

        // Assert
        Assert.assertNotEquals(false, user1_response);
        Assert.assertNotEquals(true, user2_response);
        Assert.assertNotEquals(true, user3_response);
        Assert.assertNotEquals(true, user4_response);
        Assert.assertNotEquals(true, user5_response);
    }

    @Test
    public void UserService_AvailableUsername_ReturnsBoolean() {
        // Arrange
        List<Users> users = List.of(
                new Users("User1", "Password", "email@address.com", "2001-11-20", "4242424242424242")
        );
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Users user1 = new Users("User1","Password1", "email1@address.com", "2001-11-20", "4242424242424242");
        Users user2 = new Users("User2","Password2", "email2@address.com", "2000-01-13", "");

        // Act
        boolean user1_response = userService.availableUsername(user1.getUsername());
        boolean user2_response = userService.availableUsername(user2.getUsername());

        // Assert
        Assert.assertFalse(user1_response);
        Assert.assertTrue(user2_response);
    }

    @Test
    public void UserService_OfAge_ReturnsBoolean() {
        // Arrange
        Users user1 = new Users("User1","Password1", "email1@address.com", "2001-11-20", "4242424242424242");
        Users user2 = new Users("User2","Password2", "email2@address.com", "2010-11-20", "4242424242424242");

        // Act
        boolean user1_response = userService.isOfAge(user1.getDoB());
        boolean user2_response = userService.isOfAge(user2.getDoB());

        // Assert
        Assert.assertTrue(user1_response);
        Assert.assertFalse(user2_response);
    }
}
