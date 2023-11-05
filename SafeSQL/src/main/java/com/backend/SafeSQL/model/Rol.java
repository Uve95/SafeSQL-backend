package com.backend.SafeSQL.model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    private Long rolId;

    private String rolName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rol")
    private Set<UserRol> userRoles = new HashSet();

    public Rol() {
    }

    public Rol(Long rolId, String rolName) {
        this.rolId = rolId;
        this.rolName = rolName;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public Set<UserRol> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRol> userRoles) {
        this.userRoles = userRoles;
    }
}
