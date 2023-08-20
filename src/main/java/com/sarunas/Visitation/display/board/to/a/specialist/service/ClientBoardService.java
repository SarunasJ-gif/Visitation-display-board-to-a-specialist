package com.sarunas.Visitation.display.board.to.a.specialist.service;

import com.sarunas.Visitation.display.board.to.a.specialist.entities.ClientEntity;
import com.sarunas.Visitation.display.board.to.a.specialist.entities.SpecialistEntity;
import com.sarunas.Visitation.display.board.to.a.specialist.exceptions.VisitHappeningNowException;
import com.sarunas.Visitation.display.board.to.a.specialist.exceptions.VisitIsFinishedException;
import com.sarunas.Visitation.display.board.to.a.specialist.exceptions.VisitNotFoundException;
import com.sarunas.Visitation.display.board.to.a.specialist.mapper.ClientMapper;
import com.sarunas.Visitation.display.board.to.a.specialist.model.Client;
import com.sarunas.Visitation.display.board.to.a.specialist.model.RemainingTime;
import com.sarunas.Visitation.display.board.to.a.specialist.model.VisitationTime;
import com.sarunas.Visitation.display.board.to.a.specialist.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientBoardService {

    private final ClientRepository clientRepository;
    private final SpecialistService specialistService;

    public Client createVisit(Long specialistId) {
        SpecialistEntity specialistEntity = specialistService.findSpecialistById(specialistId);
        List<ClientEntity> clients = specialistEntity.getClients();
        Long newVisitNumber = clients.isEmpty() ? 1L : clients.get(clients.size() - 1).getVisitNumber() + 1;
        LocalDateTime newVisitTime = getVisitDate(clients);
        ClientEntity newClient = ClientEntity.builder()
                .visitNumber(newVisitNumber)
                .time(newVisitTime)
                .isActive(false)
                .isFinished(false)
                .build();
        ClientEntity savedClientEntity = clientRepository.save(newClient);
        savedClientEntity.setSpecialist(specialistEntity);
        clients.add(savedClientEntity);
        specialistEntity.setClients(clients);
        specialistService.save(specialistEntity);
        return ClientMapper.toClient(savedClientEntity);
    }

    public VisitationTime getVisitTime(Long id) {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Such visit doesn't exist, not found by id " + id));
        LocalDateTime visitTime = clientEntity.getTime();
        LocalDateTime now = LocalDateTime.now();
        if (visitTime.isBefore(now)) {
            if ((visitTime.plus(15, ChronoUnit.MINUTES)).isBefore(now)) {
                throw new VisitIsFinishedException("This visit by id " + id + " has ended.");
            } else {
                throw new VisitHappeningNowException("This visit by id " + id + "is happening now.");
            }
        }
        return VisitationTime.builder().visitationTime(visitTime).build();
    }

    public RemainingTime getRemainingTimeUntilVisit(Long id) {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Such visit doesn't exist, not found by id " + id));
        LocalDateTime visitTime = clientEntity.getTime();
        LocalDateTime now = LocalDateTime.now();
        if (visitTime.isBefore(now)) {
            if ((visitTime.plus(15, ChronoUnit.MINUTES)).isBefore(now)) {
                throw new VisitIsFinishedException("This visit by id " + id + " has ended.");
            } else {
                throw new VisitHappeningNowException("This visit by id " + id + "is happening now.");
            }
        }
        Duration duration = Duration.between(now, visitTime);
        String leftTimeUntilVisit = remainingTimeUntilVisit(duration);
        return RemainingTime.builder().remainingTime(leftTimeUntilVisit).build();
    }

    public List<Client> getAllActiveVisit() {
        List<ClientEntity> activeClients = clientRepository.findAll()
                .stream().filter(ClientEntity::getIsActive).toList();
        return activeClients.stream().map(ClientMapper::toClient).toList();
    }

    public List<Client> getSevenFollowUpVisits() {
        List<ClientEntity> sevenNextClients = clientRepository.findAll().stream()
                .filter(client -> !client.getIsActive() && !client.getIsFinished()).limit(7).toList();
        return sevenNextClients.stream().map(ClientMapper::toClient).toList();
    }

    public void deleteVisit(Long id) {
        clientRepository.deleteById(id);
    }

    private LocalDateTime getVisitDate(List<ClientEntity> clients) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime visitDate = clients.isEmpty() ? now.plus(15, ChronoUnit.MINUTES)
                : clients.get(clients.size() - 1).getTime().plus(15, ChronoUnit.MINUTES);
        if (visitDate.isBefore(now) || visitDate.isEqual(now)) {
            visitDate = now.plus(15, ChronoUnit.MINUTES);
        }
        return visitDate;
    }

    private String remainingTimeUntilVisit(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return days + " days, " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
    }
}
