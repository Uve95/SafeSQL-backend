package com.backend.SafeSQL.service;

import java.util.Set;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.model.UserRol;

public interface UserService {

    public User saveUser(User user, Set<UserRol> userRoles) throws Exception;

    public User getUser(String email) throws Exception;

    public void changePassword(User user) throws Exception;

    public void forgotPassword(User user) throws Exception;

    public boolean connectBD(String[] info) throws Exception;

    public User updateUser(User user, String token) throws Exception;

    public String[] checklistConfig(String[] info) throws Exception;

    public String[] checklistNetwork(String[] info) throws Exception;

    public String[] checklistPermission(String[] info) throws Exception;

    public String[] checklistPassword(String[] info) throws Exception;

    public String[] checklistSession(String[] info) throws Exception;

    public String[] checklistMaintenance(String[] info) throws Exception;

    public String[] checklistData(String[] info) throws Exception;

    public String[] checklistRol(String[] info) throws Exception;

    public String bdName(String info) throws Exception;

    public void deleteInfo(String info) throws Exception;

    public String getToken(String info) throws Exception;

    public void setTime(String[] info) throws Exception;

    public String getTime(String info) throws Exception;

    public void setReport(String[] info) throws Exception;

    public String [] getReport(String info) throws Exception;
    
    public String getInfo(String info) throws Exception;


}
