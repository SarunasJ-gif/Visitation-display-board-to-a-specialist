package com.sarunas.Visitation.display.board.to.a.specialist.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class CreateClientVisit {

    @NotNull
    Long visitNumber;
    @NotNull
    LocalDateTime time;
    @NotNull
    Boolean isActive;
    @NotNull
    Boolean isFinished;
}
