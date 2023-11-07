package com.backend.SafeSQL.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.backend.SafeSQL.model.Rol;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.model.UserRol;
import com.backend.SafeSQL.service.UserService;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    String[] array = new String[72];

    @PostMapping("register")
    public User register(@RequestBody User user) throws Exception {
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        Set<UserRol> userRoles = new HashSet();
        Rol rol = new Rol();
        rol.setRolId(2L);
        rol.setRolName("USER");
        UserRol userRol = new UserRol();
        userRol.setUser(user);
        userRol.setRol(rol);
        userRoles.add(userRol);
        return userService.saveUser(user, userRoles);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("update/{token}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable("token") String token) throws Exception {
        if (user.getPassword().equals(""))
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        userService.updateUser(user, token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("forgotPassword")
    public void forgotPassword(@RequestBody User user) throws Exception {
        try {
            userService.forgotPassword(user);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PostMapping("changePassword")
    public void changePassword(@RequestBody User user) throws Exception {
        try {
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
            userService.changePassword(user);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("connectBD")
    public boolean connectBD(@RequestBody String[] info) throws Exception {
        try {
            boolean connected = userService.connectBD(info);
            return connected;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("actual-bd/{email}")
    public String bdName(@PathVariable("email") String email) throws Exception {
        try {
            String bdName = userService.bdName(email);
            Arrays.fill(array, null);
            return bdName;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("checklistConfiguration")
    public String[] checklistConfiguration(@RequestBody String[] info) throws Exception {
        try {
            array = userService.checklistConfig(info);
            return array;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("checklistNetwork")
    public String[] checklistNetwork(@RequestBody String[] info) throws Exception {
        try {
            array = userService.checklistNetwork(info);
            return array;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("checklistPermission")
    public String[] checklistPermission(@RequestBody String[] info) throws Exception {
        try {
            array = userService.checklistPermission(info);
            return array;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("checklistPassword")
    public String[] checklistPassword(@RequestBody String[] info) throws Exception {
        try {
            array = userService.checklistPassword(info);
            return array;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("checklistSession")
    public String[] checklistSession(@RequestBody String[] info) throws Exception {
        try {
            array = userService.checklistSession(info);
            return array;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("checklistMaintenance")
    public String[] checklistMaintenance(@RequestBody String[] info) throws Exception {
        try {
            array = userService.checklistMaintenance(info);
            return array;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("checklistData")
    public String[] checklistData(@RequestBody String[] info) throws Exception {
        try {
            array = userService.checklistData(info);
            return array;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("checklistRol")
    public String[] checklistRol(@RequestBody String[] info) throws Exception {
        try {
            return userService.checklistRol(info);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("actual-token/{email}")
    public String getToken(@PathVariable("email") String email) throws Exception {
        try {
            return userService.getToken(email);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @PostMapping("delete-info")
    public void deleteInfo(@RequestBody String info) throws Exception {
        try {
            userService.deleteInfo(info);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @PostMapping("info-time")
    public void setTime(@RequestBody String[] info) throws Exception {
        try {
            userService.setTime(info);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("info-time/{email}")
    public String getTime(@PathVariable("email") String email) throws Exception {
        try {
            return userService.getTime(email);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @PostMapping("info-report")
    public void setReport(@RequestBody String[] info) throws Exception {
        try {
            userService.setReport(info);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("info-report/{email}")
    public String [] getReport(@PathVariable("email") String email) throws Exception {
        try {
            return userService.getReport(email);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
    
    
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("info/{email}")
    public String getInfo(@PathVariable("email") String email) throws Exception {
        try {
            return userService.getInfo(email);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
