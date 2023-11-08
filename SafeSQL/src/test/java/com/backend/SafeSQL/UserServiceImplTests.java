package com.backend.SafeSQL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.backend.SafeSQL.controller.UserController;
import com.backend.SafeSQL.dao.RolRepository;
import com.backend.SafeSQL.dao.UserRepository;
import com.backend.SafeSQL.model.Rol;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.model.UserRol;
import com.backend.SafeSQL.service.MailService;
import com.backend.SafeSQL.service.UserService;
import com.backend.SafeSQL.service.UserServiceImpl;

public class UserServiceImplTests {

    private static final Object String = null;

 
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolRepository rolRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() throws Exception {
        User user = new User(null, null, null);
        user.setEmail("test@example.com");

        // Simular el comportamiento del repositorio al buscar un usuario por email
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // Simular el comportamiento del repositorio al guardar un usuario
        when(userRepository.save(any())).thenReturn(String);

        User update = userRepository.findByEmail(user.getEmail());

        assertEquals(user.getEmail(), update.getEmail());

    }


    @Test
    public void testUpdateUserWithInvalidChanges() {
        String token = "testToken";
        User existingUser = new User("testName", "testSurname", token);
        User updatedUser = new User(null, "NewSurname", token);
    
        // Simular el comportamiento del repositorio al buscar un usuario por token
        when(userRepository.findByToken(token)).thenReturn(existingUser);
    
        // Verificar que llamar a updateUser con cambios inválidos lanza una excepción
        Exception exception = assertThrows(Exception.class, () -> {
            userService.updateUser(updatedUser, token);
        });
    
        // Verificar que el mensaje de la excepción contiene el texto esperado
        assertTrue(exception.getMessage().contains("No existe el usuario con token " + token));
    
        // Verificar que el método save no se llamó
        verify(userRepository, never()).save(any(User.class));
    }
    



    @Test
    public void testUpdateUser_InvalidToken() throws Exception {
        // Datos de prueba
        String token = "invalidToken";
        User updatedUser = new User("NewName", "NewSurname", token);

        // Simular el comportamiento del repositorio al buscar un usuario por token
        when(userRepository.findByToken(token)).thenReturn(null);

        // Llamar al método updateUser con un token inválido
        userService.updateUser(updatedUser, token);
    }





}
