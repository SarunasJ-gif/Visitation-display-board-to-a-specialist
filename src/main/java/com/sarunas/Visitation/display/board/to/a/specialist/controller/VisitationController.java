package com.sarunas.Visitation.display.board.to.a.specialist.controller;

import com.sarunas.Visitation.display.board.to.a.specialist.model.Client;
import com.sarunas.Visitation.display.board.to.a.specialist.service.VisitationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitation/specialist")
@RequiredArgsConstructor
@Slf4j
public class VisitationController {

    private final VisitationService visitationService;

    @GetMapping("/next-visitation")
    public Client findNextVisitation() {
        return visitationService.findNextVisitation();
    }

    @DeleteMapping("/delete-visitation/{id}")
    public void deleteVisitById(@PathVariable Long id) {
        visitationService.deleteVisitById(id);
    }

    @DeleteMapping("/delete-all-expired-visits")
    public void deleteAllFinishedVisits() {
        visitationService.deleteAllFinishedVisits();
    }

    @GetMapping("/get-visitation/{id}")
    public Client findVisitationById(@PathVariable Long id) {
        return visitationService.findVisitationById(id);
    }

    @PatchMapping("/activate-visitation/{id}")
    public Client activateVisit(@PathVariable Long id) {
        return visitationService.activateVisit(id);
    }

    @PatchMapping("/finish-visitation/{id}")
    public Client endVisit(@PathVariable Long id) {
        return visitationService.endVisit(id);
    }
}



