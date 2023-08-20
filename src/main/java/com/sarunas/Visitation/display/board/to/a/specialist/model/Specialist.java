package com.sarunas.Visitation.display.board.to.a.specialist.model;

import com.sarunas.Visitation.display.board.to.a.specialist.entities.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Specialist {

    @NotNull
    Long id;
    @NotNull
    String username;
    @NotEmpty
    List<Role> roles;
    @NotNull
    String email;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    List<Client> clients;
    Boolean isBusy;
}
