package com.backend.SafeSQL.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.SafeSQL.model.Rol;

@Repository

public interface RolRepository extends JpaRepository<Rol, Long> {
	
}
