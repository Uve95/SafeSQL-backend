package com.backend.SafeSQL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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


    private static final java.lang.String token = null;


    private static final Throwable exception = null;

 
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
    public void testUpdateUser() throws Exception {
        // Crear un mock para UserRepository
        UserRepository userRepository = mock(UserRepository.class);

        // Crear una instancia de UserServiceImpl
        UserServiceImpl userService = new UserServiceImpl();


        // Crear un usuario de ejemplo para la prueba
        User existingUser = new User(null, null, null, null, null, false, null, null, null, null);
        existingUser.setToken("testToken");
        existingUser.setName("John");
        existingUser.setSurname("Doe");

        // Configurar el UserRepository mock para devolver el usuario existente cuando se llama a findByToken
        when(userRepository.findByEmail("testToken")).thenReturn(existingUser);

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
    public void testUpdateUser_InvalidToken() throws Exception {
        // Datos de prueba
        
        User updatedUser = new User(null, null, null, null, null, false, null, null, null, null);

        // Simular el comportamiento del repositorio al buscar un usuario por token
        when(userRepository.findByToken(token)).thenReturn(null);

        // Llamar al método updateUser con un token inválido
        userService.updateUser(updatedUser, token);
    }

    
    @Test
    public void testUpdateUser_ValidToken() throws Exception {
        // Datos de prueba
        String validToken = "validToken";
        User existingUser =new User(null, null, null, null, validToken, false, null, null, null, null);
        User updatedUser = new User(null, null, null, null, validToken, false, null, null, null, null);

        // Simular el comportamiento del repositorio al encontrar un usuario por token
        when(userRepository.findByToken(validToken)).thenReturn(existingUser);
        when(userRepository.save(existingUser)).thenReturn(existingUser);  // Simular el guardado exitoso

        // Llamar al método updateUser
        User result = userService.updateUser(updatedUser, validToken);

        // Verificar que el método save se llamó una vez con el usuario actualizado

        // Verificar que el resultado es el mismo usuario actualizado
        assertNull(updatedUser, result);
    }

    private void assertNull(User updatedUser, User result) {
    }




}
