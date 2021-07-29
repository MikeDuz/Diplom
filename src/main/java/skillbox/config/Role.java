package skillbox.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import skillbox.config.Permission;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.USER)),
    MODERATOR(Set.of(Permission.MODERATE, Permission.USER));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream().map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
    }
}
