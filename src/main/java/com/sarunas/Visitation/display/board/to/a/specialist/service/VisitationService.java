package com.sarunas.Visitation.display.board.to.a.specialist.service;

import com.sarunas.Visitation.display.board.to.a.specialist.entities.ClientEntity;
import com.sarunas.Visitation.display.board.to.a.specialist.entities.SpecialistEntity;
import com.sarunas.Visitation.display.board.to.a.specialist.exceptions.SpecialistIsBusyException;
import com.sarunas.Visitation.display.board.to.a.specialist.exceptions.SpecialistNotFoundException;
import com.sarunas.Visitation.display.board.to.a.specialist.exceptions.VisitNotFoundException;
import com.sarunas.Visitation.display.board.to.a.specialist.mapper.ClientMapper;
import com.sarunas.Visitation.display.board.to.a.specialist.model.Client;
import com.sarunas.Visitation.display.board.to.a.specialist.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SPECIALIST', 'ADMIN')")
@Transactional
public class VisitationService {

    private final ClientRepository clientRepository;
    private final SpecialistService specialistService;

    public Client findNextVisitation() {
        SpecialistEntity specialistEntity = getSpecialistFromContext();
        List<ClientEntity> clients = specialistEntity.getClients();
        List<ClientEntity> checkedIfTimeIsNotFinishedVisits = checkIfVisitTimeIsNotFinished(clients, specialistEntity);
        ClientEntity activeVisit = checkedIfTimeIsNotFinishedVisits.stream()
                .filter(client -> !client.getIsFinished() && !client.getIsActive()).findFirst()
                .orElseThrow(() -> new VisitNotFoundException("You have no visits."));
        return ClientMapper.toClient(activeVisit);
    }

    public void deleteVisitById(Long id) {
        SpecialistEntity specialistEntity = getSpecialistFromContext();
        List<ClientEntity> clients = specialistEntity.getClients();
        if (clients.stream().anyMatch(client -> client.getId().equals(id) && client.getIsActive())) {
            removeClientById(id, clients, specialistEntity);
            specialistEntity.setIsBusy(false);
        }
        if (clients.stream().anyMatch(client -> client.getId().equals(id))) {
            removeClientById(id, clients, specialistEntity);
        }
    }

    public void deleteAllFinishedVisits() {
        SpecialistEntity specialistEntity = getSpecialistFromContext();
        List<ClientEntity> checkedIfTimeIsNotFinishedVisits = checkIfVisitTimeIsNotFinished(specialistEntity.getClients(), specialistEntity);
        checkedIfTimeIsNotFinishedVisits.stream().filter(client ->
                client.getIsFinished() && !client.getIsActive()).forEach(clientRepository::delete);
        checkedIfTimeIsNotFinishedVisits.removeIf(client -> client.getIsFinished() && !client.getIsActive());
        specialistEntity.setClients(checkedIfTimeIsNotFinishedVisits);
    }

    public Client findVisitationById(Long id) {
        SpecialistEntity specialistEntity = getSpecialistFromContext();
        ClientEntity clientEntity = specialistEntity.getClients().stream()
                .filter(client -> client.getId().equals(id) && !client.getIsActive() && !client.getIsFinished())
                .findFirst().orElseThrow(() -> new VisitNotFoundException("You have no such visit."));
        return ClientMapper.toClient(clientEntity);
    }

    public Client activateVisit(Long id) {
        SpecialistEntity specialistEntity = getSpecialistFromContext();
        if (specialistEntity.getIsBusy()) {
            throw new SpecialistIsBusyException("Dear " + specialistEntity.getUsername() +
                    ", you have client, you cannot accept another client!");
        }
        List<ClientEntity> clients = checkIfVisitTimeIsNotFinished(specialistEntity.getClients(), specialistEntity);
        ClientEntity updatedClient = clients.stream()
                .filter(client -> client.getId().equals(id) && !client.getIsActive() && !client.getIsFinished())
                .findFirst()
                .orElseThrow(() -> new VisitNotFoundException("You have no such visit."));
            updatedClient.setIsActive(true);
            clientRepository.save(updatedClient);
            specialistEntity.setIsBusy(true);
            specialistService.save(specialistEntity);
        return ClientMapper.toClient(updatedClient);
    }

    public Client endVisit(Long id) {
        SpecialistEntity specialistEntity = getSpecialistFromContext();
        List<ClientEntity> clients = checkIfVisitTimeIsNotFinished(specialistEntity.getClients(), specialistEntity);
        ClientEntity updatedClient = clients.stream()
                .filter(client -> client.getId().equals(id) && client.getIsActive() && !client.getIsFinished())
                .findFirst()
                .orElseThrow(() -> new VisitNotFoundException("You have no such active visit."));
        updatedClient.setIsActive(false);
        updatedClient.setIsFinished(true);
        clientRepository.save(updatedClient);
        specialistEntity.setIsBusy(false);
        specialistService.save(specialistEntity);
        return ClientMapper.toClient(updatedClient);
    }

    private SpecialistEntity getSpecialistFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return specialistService.findByUsername(username)
                .orElseThrow(() -> new SpecialistNotFoundException("User must be authenticated."));
    }

    private List<ClientEntity> checkIfVisitTimeIsNotFinished(List<ClientEntity> clients, SpecialistEntity specialistEntity) {
        clients.stream().filter(client -> client.getTime().isBefore(LocalDateTime.now())).forEach(
            client -> {
                client.setIsActive(false);
                client.setIsFinished(true);
            });
        specialistEntity.setClients(clients);
        clients.forEach(client -> client.setSpecialist(specialistEntity));
        specialistService.save(specialistEntity);
        return clients;
    }

    private void removeClientById(Long id, List<ClientEntity> clients, SpecialistEntity specialistEntity) {
        clientRepository.deleteById(id);
        clients.removeIf(client -> client.getId().equals(id));
        specialistEntity.setClients(clients);
    }
}



