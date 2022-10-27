package com.example.blogservice.entity.role;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.USERS_PUBLISH_POSTS)),
    ADMIN(Set.of(Permission.ADMINS_SEE_ADMINS,
            Permission.ADMINS_UPDATE,
            Permission.ADMINS_MAKE_ADMINS,
            Permission.ADMINS_READ_ADMIN_ROLES,
            Permission.ADMINS_MODIFY_ROLES,
            Permission.ADMINS_MODIFY_USERS,
            Permission.ADMINS_SEE_ADMINS,
            Permission.ADMINS_READ_ALL_POSTS));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
