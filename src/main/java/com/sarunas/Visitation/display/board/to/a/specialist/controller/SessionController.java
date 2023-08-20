package com.sarunas.Visitation.display.board.to.a.specialist.controller;

import com.sarunas.Visitation.display.board.to.a.specialist.model.LoginData;
import com.sarunas.Visitation.display.board.to.a.specialist.model.Session;
import com.sarunas.Visitation.display.board.to.a.specialist.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    @GetMapping
    public Session getSession() {
        return sessionService.getSession();
    }

    @PostMapping
    public Session createSession(HttpServletRequest httpServletRequest,
                                 @Valid @RequestBody LoginData loginData) {
        return sessionService.createSession(httpServletRequest,
                loginData.getUsername(), loginData.getPassword());
    }
}
