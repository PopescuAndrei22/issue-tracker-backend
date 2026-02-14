package com.example.issuetracker.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.domain.models.Role;

public class UserTest {

    private static User user;

    @BeforeAll
    static void initialSetUp(){
        String email = "Andrew@mycompany.com";
        String username = "andrew123";
        String password = "somepassword";
        Role role = Role.USER;

        user = new User(username, password, email, role);
    }

    @Test
    void shouldThrowErrorWhenUsernameIsMissing(){
        assertThatThrownBy(() -> 
                new User(null, "password", "email@company.com", Role.ADMIN))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> 
                new User("", "password", "email@company.com", Role.ADMIN))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowErrorWhenPasswordIsMissing(){
        assertThatThrownBy(() -> 
                new User("username", null, "email@company.com", Role.ADMIN))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> 
                new User("username", "", "email@company.com", Role.ADMIN))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowErrorWhenEmailIsMissing(){
        assertThatThrownBy(() -> 
                new User("username", "password", null , Role.ADMIN))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> 
                new User("username", "password", "" , Role.ADMIN))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowErrorWhenRoleIsMissing(){
        assertThatThrownBy(() -> 
                new User("username", "password", "email@company.com" , null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotThrowErrorUponInstantiation(){
        assertDoesNotThrow(() ->
            new User("username", "password", "email@company.com" , Role.MANAGER)
        );
    }

    @Test
    void shouldChangeTheEmailCorrectly(){

        String testEmail = "testemail@mycompany.com";
        user.changeEmail(testEmail);

        assertEquals(user.getEmail(), testEmail, "The email cannot be changed correctly");

    }

    @Test
    void shouldChangeTheRoleCorrectly(){
        Role testRole = Role.ADMIN;
        user.changeRole(testRole);

        assertEquals(user.getRole(), testRole, "The role cannot be changed correctly");
    }
}
