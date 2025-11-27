package com.api.cardlink.Controller;

import com.api.cardlink.Dto.UserDto.ConnectionDto;
import com.api.cardlink.Dto.UserDto.UserRequestDto;
import com.api.cardlink.Dto.UserDto.UserResponseDto;
import com.api.cardlink.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // Création d'un User
    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto dto) {
        log.info("Création d'un User {}", dto);
        return ResponseEntity.ok(userService.create(dto));
    }

    // Connexion d'un user
    @PostMapping("/login")
    public ResponseEntity<?> connexion(@RequestBody @Valid ConnectionDto request) {
        boolean connected = userService.connexion(request);
        if(connected){
            return ResponseEntity.ok("Connexion reussie");
        }
        return  ResponseEntity.notFound().build();
    }

    // Liste de tous les utilisateurs
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    // Récupérer un user par ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Modifier un user
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable String id, @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    // Supprimer un user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès");
    }
}
