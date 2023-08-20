package com.sarunas.Visitation.display.board.to.a.specialist.controller;

import com.sarunas.Visitation.display.board.to.a.specialist.model.CreateSpecialistForm;
import com.sarunas.Visitation.display.board.to.a.specialist.model.EditSpecialistForm;
import com.sarunas.Visitation.display.board.to.a.specialist.model.Specialist;
import com.sarunas.Visitation.display.board.to.a.specialist.service.SpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialist")
@RequiredArgsConstructor
public class SpecialistController {

    private final SpecialistService specialistService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Specialist> getAllSpecialists() {
        return specialistService.getAllSpecialists();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Specialist getSpecialistById(@RequestParam Long id) {
        return specialistService.getSpecialistById(id);
    }

    @PostMapping("/add")
    public Specialist createSpecialist(@Valid @RequestBody CreateSpecialistForm createSpecialistForm) {
        return specialistService.createSpecialist(createSpecialistForm);
    }

    @PutMapping
    public Specialist editSpecialist(@Valid @RequestBody EditSpecialistForm editUserForm) {
        return specialistService.editSpecialist(editUserForm);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Specialist editSpecialistByAdmin(@PathVariable Long id, @Valid @RequestBody EditSpecialistForm editUserForm) {
        return specialistService.editSpecialistByAdmin(id, editUserForm);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSpecialistById(@PathVariable Long id) {
        specialistService.deleteSpecialistById(id);
    }
}
