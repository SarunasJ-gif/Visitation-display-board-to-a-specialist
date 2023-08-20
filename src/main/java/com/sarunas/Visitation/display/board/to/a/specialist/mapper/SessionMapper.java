package com.sarunas.Visitation.display.board.to.a.specialist.mapper;

import com.sarunas.Visitation.display.board.to.a.specialist.authorization.CustomUserDetails;
import com.sarunas.Visitation.display.board.to.a.specialist.model.Session;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionMapper {

    public static Session toSession(CustomUserDetails currentUser) {
        return Session.builder()
                .username(Optional.ofNullable(currentUser).map(CustomUserDetails::getUsername)
                .orElse(null))
                .build();
    }
}
