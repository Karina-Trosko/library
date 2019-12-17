package edu.grsu.karina.library.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,ADMIN,EMPLOYEE;

    @Override
    public String getAuthority(){
        return name();
    }
}
