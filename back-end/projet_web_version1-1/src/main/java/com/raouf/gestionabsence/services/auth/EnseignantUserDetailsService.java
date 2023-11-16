package com.raouf.gestionabsence.services.auth;

import com.raouf.gestionabsence.entities.Enseignant;
import com.raouf.gestionabsence.repository.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EnseignantUserDetailsService implements UserDetailsService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Enseignant enseignant = enseignantRepository.findByEmail(email);
        System.out.println(enseignant);
        if (enseignant == null) {
            throw new UsernameNotFoundException("Teacher not found with email: " + email);
        }

        return enseignant;
    }


   /* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }*/

}
