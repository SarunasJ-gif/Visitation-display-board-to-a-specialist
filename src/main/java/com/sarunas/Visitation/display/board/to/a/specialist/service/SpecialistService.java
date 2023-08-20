package com.sarunas.Visitation.display.board.to.a.specialist.service;

import com.sarunas.Visitation.display.board.to.a.specialist.entities.ClientEntity;
import com.sarunas.Visitation.display.board.to.a.specialist.entities.SpecialistEntity;
import com.sarunas.Visitation.display.board.to.a.specialist.exceptions.ForbiddenDeleteSpecialistException;
import com.sarunas.Visitation.display.board.to.a.specialist.exceptions.SpecialistExistsException;
import com.sarunas.Visitation.display.board.to.a.specialist.exceptions.SpecialistNotFoundException;
import com.sarunas.Visitation.display.board.to.a.specialist.mapper.SpecialistMapper;
import com.sarunas.Visitation.display.board.to.a.specialist.model.CreateSpecialistForm;
import com.sarunas.Visitation.display.board.to.a.specialist.model.EditSpecialistForm;
import com.sarunas.Visitation.display.board.to.a.specialist.model.Specialist;
import com.sarunas.Visitation.display.board.to.a.specialist.repository.SpecialistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Specialist> getAllSpecialists() {
        return specialistRepository.findAll().stream()
                .map(SpecialistMapper::toSpecialist).toList();
    }

    public Specialist getSpecialistById(Long id) {
        SpecialistEntity specialistEntity = specialistRepository.findById(id)
                .orElseThrow(() -> new SpecialistNotFoundException("Such user doesn't exist, not found by id " + id));
        return SpecialistMapper.toSpecialist(specialistEntity);
    }


    public Specialist createSpecialist(CreateSpecialistForm createSpecialistForm) {
        Optional<SpecialistEntity> optionalSpecialistEntity = specialistRepository.findByEmail(createSpecialistForm.getEmail());
        if (optionalSpecialistEntity.isPresent()) {
            throw new SpecialistExistsException("Such user with email " + createSpecialistForm.getEmail() + " already exists!");
        }
        String password = createSpecialistForm.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        log.info("Your password: " + password);
        SpecialistEntity createdUser = specialistRepository.save(SpecialistMapper.createToSpecialistEntity(createSpecialistForm, encodedPassword));
        return SpecialistMapper.toSpecialist(createdUser);
    }

    public Specialist editSpecialist(EditSpecialistForm editSpecialistForm) {
        SpecialistEntity specialistFromContext = getSpecialistFromContext();
        List<ClientEntity> clients = specialistFromContext.getClients();
        Boolean isBusy = specialistFromContext.getIsBusy();
        SpecialistEntity specialistEntity = SpecialistMapper.editToSpecialistEntity(specialistFromContext.getId(), editSpecialistForm);
        specialistEntity.setClients(clients);
        specialistEntity.setIsBusy(isBusy);
        clients.forEach(client -> client.setSpecialist(specialistEntity));
        SpecialistEntity editedSpecialistEntity = specialistRepository.save(specialistEntity);
        return SpecialistMapper.toSpecialist(editedSpecialistEntity);
    }

    public Specialist editSpecialistByAdmin(Long id, EditSpecialistForm editSpecialistForm) {
        SpecialistEntity specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new SpecialistNotFoundException("Such user doesn't exist, not found by id " + id));
        List<ClientEntity> clients = specialist.getClients();
        Boolean isBusy = specialist.getIsBusy();
        SpecialistEntity specialistEntity = SpecialistMapper.editToSpecialistEntity(id, editSpecialistForm);
        specialistEntity.setClients(clients);
        specialistEntity.setIsBusy(isBusy);
        clients.forEach(client -> client.setSpecialist(specialistEntity));
        SpecialistEntity editedSpecialistEntity = specialistRepository.save(specialistEntity);
        return SpecialistMapper.toSpecialist(editedSpecialistEntity);
    }

    public void deleteSpecialistById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        SpecialistEntity currentUser = specialistRepository.findByUsername(username)
                .orElseThrow(() -> new SpecialistNotFoundException("User must be authenticated."));
        if (Objects.equals(currentUser.getId(), id)) {
            throw new ForbiddenDeleteSpecialistException("Forbidden delete Admin!");
        }
        specialistRepository.deleteById(id);
    }

    public Optional<SpecialistEntity> findByUsername(String username) {
        return specialistRepository.findByUsername(username);
    }

    public void save(SpecialistEntity specialistEntity) {
        specialistRepository.save(specialistEntity);
    }

    public SpecialistEntity findSpecialistById(Long id) {
        return specialistRepository.findById(id)
                .orElseThrow(() -> new SpecialistNotFoundException("Such user doesn't exist, not found by id " + id));
    }

    private SpecialistEntity getSpecialistFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return specialistRepository.findByUsername(username)
                .orElseThrow(() -> new SpecialistNotFoundException("User must be authenticated."));
    }
}
