package com.reply.streaming.users;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_AddUser_ReturnsSavedUser() {
        // Arrange
        Users user = new Users("User1","Password1", "email1@address1.com", "2001-11-20", "4242424242424242");

        // Act
        Users savedUser = userRepository.save(user);

        // Assert
        Assert.assertNotNull(savedUser);
        Assert.assertEquals(user.getUsername(), savedUser.getUsername());
    }

    @Test
    public void UserRepository_GetAll_ReturnsAllUsers() {
        // Arrange
        Users user1 = new Users("User1","Password1", "email1@address.com", "2001-11-20", "4242424242424242");
        Users user2 = new Users("User2","Password2", "email2@address.com", "2000-01-13", "");
        userRepository.save(user1);
        userRepository.save(user2);

        //Act
        List<Users> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        //Assert
        Assert.assertFalse(users.size() != 2);
    }
}
