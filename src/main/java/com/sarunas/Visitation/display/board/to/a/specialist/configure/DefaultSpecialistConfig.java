package com.sarunas.Visitation.display.board.to.a.specialist.configure;

import com.sarunas.Visitation.display.board.to.a.specialist.entities.Role;
import com.sarunas.Visitation.display.board.to.a.specialist.entities.SpecialistEntity;
import com.sarunas.Visitation.display.board.to.a.specialist.repository.SpecialistRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@AllArgsConstructor
public class DefaultSpecialistConfig {

    private final SpecialistRepository specialistRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void insertAdminSpecialistDefaultData() {
        SpecialistEntity specialistEntity = SpecialistEntity.builder()
                .username("SarunasJ")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList(Role.ADMIN))
                .email("sarunas@mail.com")
                .firstName("Sarunas")
                .lastName("Jurevicius")
                .isBusy(false)
                .build();
        specialistRepository.save(specialistEntity);
    }
}
