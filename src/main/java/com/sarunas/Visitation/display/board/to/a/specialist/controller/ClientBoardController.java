package com.sarunas.Visitation.display.board.to.a.specialist.controller;

import com.sarunas.Visitation.display.board.to.a.specialist.model.Client;
import com.sarunas.Visitation.display.board.to.a.specialist.model.RemainingTime;
import com.sarunas.Visitation.display.board.to.a.specialist.model.VisitationTime;
import com.sarunas.Visitation.display.board.to.a.specialist.service.ClientBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/board")
@RequiredArgsConstructor
public class ClientBoardController {

    private final ClientBoardService clientBoardService;

    @PostMapping("/{specialistId}")
    public Client createVisit(@PathVariable Long specialistId) {
        return clientBoardService.createVisit(specialistId);
    }

    @GetMapping("/remaining-time/{id}")
    public RemainingTime getRemainingTimeUntilVisit(@PathVariable Long id) {
        return clientBoardService.getRemainingTimeUntilVisit(id);
    }

    @GetMapping("/visit-time/{id}")
    public VisitationTime getVisitTime(@PathVariable Long id) {
        return clientBoardService.getVisitTime(id);
    }

    @GetMapping("/active-visits")
    public List<Client> getAllActiveVisit() {
        return clientBoardService.getAllActiveVisit();
    }

    @GetMapping("/seven-next-visits")
    public List<Client> getSevenFollowUpVisits() {
        return clientBoardService.getSevenFollowUpVisits();
    }

    @DeleteMapping("/{id}")
    public void deleteVisit(@PathVariable Long id) {
        clientBoardService.deleteVisit(id);
    }
}
