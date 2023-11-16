package com.raouf.gestionabsence.entities;



import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.raouf.gestionabsence.entities.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    ENSEIGNANT(
            Set.of(
                    ENSEIGNANT_READ,
                    ENSEIGNANT_UPDATE,
                    ENSEIGNANT_DELETE,
                    ENSEIGNANT_CREATE
            )
    ),
    ETUDIANT(
            Set.of(
                    ETUDIANT_READ,
                    ETUDIANT_UPDATE,
                    ETUDIANT_DELETE,
                    ETUDIANT_CREATE
            )
    )

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
