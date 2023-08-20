package com.sarunas.Visitation.display.board.to.a.specialist.authorization;

import com.sarunas.Visitation.display.board.to.a.specialist.repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SpecialistRepository specialistRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return specialistRepository.findByUsername(username).map(user -> new CustomUserDetails(
                user.getUsername(),
                user.getRoles(),
                user.getPassword()
        )).orElseThrow(() -> new UsernameNotFoundException("User not found by username " + username));
    }
}
