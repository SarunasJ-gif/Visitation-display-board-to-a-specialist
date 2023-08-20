package com.sarunas.Visitation.display.board.to.a.specialist.service;

import com.sarunas.Visitation.display.board.to.a.specialist.authorization.CustomUserDetails;
import com.sarunas.Visitation.display.board.to.a.specialist.authorization.SecurityContextService;
import com.sarunas.Visitation.display.board.to.a.specialist.mapper.SessionMapper;
import com.sarunas.Visitation.display.board.to.a.specialist.model.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionService {

    private final SecurityContextService contextService;

    public Session getSession() {
        CustomUserDetails currentUser = contextService.getCurrentUser();
        return SessionMapper.toSession(currentUser);
    }

    public Session createSession(HttpServletRequest httpServletRequest, String username, String password) {
        CustomUserDetails userDetails = contextService.createSession(httpServletRequest, username, password);
        return SessionMapper.toSession(userDetails);
    }
}
