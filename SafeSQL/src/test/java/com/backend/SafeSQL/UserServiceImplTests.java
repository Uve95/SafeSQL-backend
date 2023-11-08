package com.backend.SafeSQL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.backend.SafeSQL.dao.RolRepository;
import com.backend.SafeSQL.dao.UserRepository;
import com.backend.SafeSQL.model.Rol;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.model.UserRol;
import com.backend.SafeSQL.service.MailService;
import com.backend.SafeSQL.service.UserServiceImpl;

import io.jsonwebtoken.lang.Assert;
import jakarta.mail.MessagingException;

public class UserServiceImplTests {
    private static final User String = null;

    private static final List<User> ArrayList = null;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private MailService mailService;

    @Before(value = "")
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Mock data and behavior for userRepository
        User user = new User(null, null, null);
        user.setToken("token");
        Mockito.when(userRepository.findByToken("token")).thenReturn(user);

        User updatedUser = new User(null, null, null);
        updatedUser.setName("NewName");
        updatedUser.setSurname("NewSurname");

        // Test updateUser method
        User result = userService.updateUser(updatedUser, "token");

        assertEquals("NewName", result.getName());
        assertEquals("NewSurname", result.getSurname());

        // Verify that userRepository.save is called
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void testSaveUser() throws Exception {
        // Configurar el comportamiento simulado del UserRepository y RolRepository
        String email = "test@example.com";
        User newUser = new User(email, email, null);
        newUser.setEmail(email);
        Set<UserRol> userRoles = new HashSet<>(); // Configurar los roles según sea necesario

        Mockito.when(userRepository.findByEmail(email)).thenReturn(null);
        Mockito.when(rolRepository.save(Mockito.any(Rol.class))).thenReturn(new Rol()); // Si es necesario

        // Ejecutar el método que se va a probar
        User savedUser = userService.saveUser(newUser, userRoles);

        // Realizar las aserciones
        Assert.notNull(savedUser);
        Assert.notNull(email, savedUser.getEmail());
        // Agregar más aserciones según lo necesario

        // Verificar que los métodos de repositorio se llamaron correctamente
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
        // Verificar otros métodos según sea necesario
    }

    @Test
    public void testFindUserById() {
        // Mock data and behavior for userRepository
        User user = new User(null, null, null);
        user.setId(1L);
        Mockito.when(userRepository.findByToken("TOKEN")).thenReturn(String);

        // Test findUserById method
        User result = userRepository.findByToken("TOKEN");

        assertNotNull(result);
        assertEquals(1L, result.getId().toString());
    }

    @Test
    public void testCreateUser() throws Exception {
        // Mock data and behavior for userRepository
        User newUser = new User(null, null, null);
        newUser.setEmail("test@example.com");
        newUser.setPassword("password");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(newUser);

        // Test createUser method
        User result = userService.saveUser(newUser, null);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        // You can add more assertions to test other properties.
    }

    @Test
    public void testDeleteUser() {
        // Mock data and behavior for userRepository
        User user = new User(null, null, null);
        user.setId(1L);
        Mockito.when(userRepository.findByToken("TOKEN")).thenReturn(String);

        // Test deleteUser method
        userRepository.deleteById("TOKEN");

        // Verify that userRepository.deleteById is called
        Mockito.verify(userRepository).deleteById("TOKEN");
    }

 

    @Test
    public void testGetUserRoles() {
        // Mock data and behavior for rolRepository
        User user = new User(null, null, null);
        user.setId(1L);

        Rol adminRole = new Rol();
        adminRole.setRolName("ADMIN");
        adminRole.setRolId(1L);

        Mockito.when(userRepository.findById("TOKEN")).thenReturn(Optional.of(user));

        // Test getUserRoles method
        List<Rol> roles = (List<Rol>) userService.getUser("EMAIL");

        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals("ADMIN", roles.get(0).getRolName());
    }

    @Test
    public void testSendPasswordResetEmail() throws MessagingException, javax.mail.MessagingException {
        // Mock data and behavior for mailService
        User user = new User(null, null, null);
        user.setEmail("test@example.com");

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        // Test sendPasswordResetEmail method
        mailService.sendEmail(user);

        // Verify that mailService.sendPasswordResetEmail is called
        Mockito.verify(mailService).sendPasswordResetEmail(user);
    }

    @Test
    public void testGetAllUsers() {
        // Mock data and behavior for userRepository
        User user1 = new User(null, null, null);
        user1.setId(1L);
        user1.setEmail("user1@example.com");

        User user2 = new User(null, null, null);
        user2.setId(2L);
        user2.setEmail("user2@example.com");

        List<User> userList = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        // Test getAllUsers method
        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1@example.com", result.get(0).getEmail());
        assertEquals("user2@example.com", result.get(1).getEmail());
    }

    @Test
    public void testAssignRoleToUser() {
        // Mock data and behavior for userRepository and rolRepository
        User user = new User(null, null, null);
        user.setId(1L);
        user.setEmail("user@example.com");

        Rol role = new Rol();
        role.setRolId(1L);
        role.setRolName("Admin");

        when(userRepository.findByToken("TOKEN")).thenReturn(String);

        // Test assignRoleToUser method
        boolean result = userService.assignRoleToUser(1L, "Admin");

        assertTrue(result);
        // Verify that the user's roles are updated
        verify(userRepository).save(user);
    }

    @Test
    public void testSendWelcomeEmail() throws MessagingException, javax.mail.MessagingException {
        // Mock data and behavior for userRepository and mailService
        User user = new User(null, null, null);
        user.setId(1L);
        user.setEmail("user@example.com");

        when(userRepository.findAll()).thenReturn(ArrayList);
        doNothing().when(mailService).sendEmail(user);

        // Test sendWelcomeEmail method
        userService.sendEmail(user);

        // Verify that the mailService method was called
        verify(mailService).sendEmail(user);
    }
}
