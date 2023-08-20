package com.sarunas.Visitation.display.board.to.a.specialist.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginData {

    @NotNull
    String username;
    @NotNull
    String password;
}
