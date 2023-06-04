package com.backend.SafeSQL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.SafeSQL.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
	
}
