package com.backend.SafeSQL;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.util.ReflectionTestUtils;

import com.backend.SafeSQL.dao.UserRepository;
import com.backend.SafeSQL.model.NumberGenerator;
import com.backend.SafeSQL.model.User;

import java.util.Arrays;
import java.util.List;


public class NumberGeneratorTests {

    @InjectMocks
    private NumberGenerator numberGenerator;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void ejecutarTarea_UpdateUsersWithTokens() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        // Act
        numberGenerator.ejecutarTarea();

        // Assert
        verify(userRepository, times(2)).save(any(User.class));
        assertNotNull(user1.getToken());
        assertNotNull(user2.getToken());
    }

    @Test
    public void ejecutarTarea_UpdateUsersWithRandomTokens() {
        // Arrange
        User user1 = new User(null, null, null, null, null, false, null, null, null, null);
        User user2 = new User(null, null, null, null, null, false, null, null, null, null);
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        // Manually set tokens to check if they change
        user1.setToken("123");
        user2.setToken("456");

        // Act
        numberGenerator.ejecutarTarea();

        // Assert
        verify(userRepository, times(2)).save(any(User.class));
        assertNotEquals("123", user1.getToken());
        assertNotEquals("456", user2.getToken());
    }
}
