package com.sarunas.Visitation.display.board.to.a.specialist.mapper;

import com.sarunas.Visitation.display.board.to.a.specialist.entities.ClientEntity;
import com.sarunas.Visitation.display.board.to.a.specialist.model.Client;
import com.sarunas.Visitation.display.board.to.a.specialist.model.CreateClientVisit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static ClientEntity toClientEntity(CreateClientVisit clientVisit) {
        return ClientEntity.builder()
                .visitNumber(clientVisit.getVisitNumber())
                .time(clientVisit.getTime())
                .isActive(clientVisit.getIsActive())
                .isFinished(clientVisit.getIsFinished())
                .build();
    }

    public static Client toClient(ClientEntity clientEntity) {
        return Client.builder()
                .id(clientEntity.getId())
                .visitNumber(clientEntity.getVisitNumber())
                .time(clientEntity.getTime())
                .isActive(clientEntity.getIsActive())
                .isFinished(clientEntity.getIsFinished())
                .specialistFirstName(clientEntity.getSpecialist().getFirstName())
                .specialistLastName(clientEntity.getSpecialist().getLastName())
                .build();
    }
}
