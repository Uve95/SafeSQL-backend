package com.backend.SafeSQL.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.SafeSQL.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    public User findByEmail(String email);

    public User findByToken(String token);
}
