package com.topjava.lunchroom_vote.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Alima-T 11/27/2022
 */
public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}