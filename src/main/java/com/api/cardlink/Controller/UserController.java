package com.api.cardlink.Controller;

import com.api.cardlink.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")//Routes
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService utilisateurService;

    // creation dun User a partir dun Dto
    @PostMapping("/create")
    public UtilisateurDto create(@RequestBody UtilisateurDto dto) {
        log.info("Creation d'un utilisateur {}", dto);
        return utilisateurService.create(dto);
    }
}
