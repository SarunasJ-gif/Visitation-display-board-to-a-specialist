package com.sarunas.Visitation.display.board.to.a.specialist.mapper;

import com.sarunas.Visitation.display.board.to.a.specialist.entities.SpecialistEntity;
import com.sarunas.Visitation.display.board.to.a.specialist.model.CreateSpecialistForm;
import com.sarunas.Visitation.display.board.to.a.specialist.model.EditSpecialistForm;
import com.sarunas.Visitation.display.board.to.a.specialist.model.Specialist;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpecialistMapper {

    public static Specialist toSpecialist(SpecialistEntity specialistEntity) {
        return Specialist.builder()
                .id(specialistEntity.getId())
                .username(specialistEntity.getUsername())
                .roles(specialistEntity.getRoles())
                .email(specialistEntity.getEmail())
                .firstName(specialistEntity.getFirstName())
                .lastName(specialistEntity.getLastName())
                .clients(specialistEntity.getClients().stream().map(ClientMapper::toClient).toList())
                .isBusy(specialistEntity.getIsBusy())
                .build();
    }


    public static SpecialistEntity createToSpecialistEntity(CreateSpecialistForm createSpecialistForm, String password) {
        return SpecialistEntity.builder()
                .username(createSpecialistForm.getUsername())
                .password(password)
                .roles(createSpecialistForm.getRoles())
                .email(createSpecialistForm.getEmail())
                .firstName(createSpecialistForm.getFirstName())
                .lastName(createSpecialistForm.getLastName())
                .clients(new ArrayList<>())
                .isBusy(false)
                .build();
    }

    public static SpecialistEntity editToSpecialistEntity(Long id, EditSpecialistForm editSpecialistForm) {
        return SpecialistEntity.builder()
                .id(id)
                .username(editSpecialistForm.getUsername())
                .password(editSpecialistForm.getPassword())
                .roles(editSpecialistForm.getRoles())
                .email(editSpecialistForm.getEmail())
                .firstName(editSpecialistForm.getFirstName())
                .lastName(editSpecialistForm.getLastName())
                .build();
    }
}
