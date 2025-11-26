package com.api.cardlink.Service;

import com.api.cardlink.Entity.User;
import com.api.cardlink.Mapper.UserMapper;
import com.api.cardlink.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


//Cretion dun user
    @Override
    public User createUser(User user) {
        // Vérification email existant
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà.");
        }

        // Hash du mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    //Retrouver un user par son id

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }


    //Retrouver un user par son mail
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec cet email"));
    }

    //Obtenir la liste de tous les users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    //Modifier les attributs dun user

    @Override
    public User updateUser(UUID id, User updatedUser) {
        User existingUser = getUserById(id);

        if (updatedUser.getFirstName() != null)
            existingUser.setFirstName(updatedUser.getFirstName());

        if (updatedUser.getLastName() != null)
            existingUser.setLastName(updatedUser.getLastName());

        if (updatedUser.getEmail() != null)
            existingUser.setEmail(updatedUser.getEmail());

        if (updatedUser.getPassword() != null)
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        return userRepository.save(existingUser);
    }


    //Supprimer un user
    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : utilisateur inexistant");
        }

        userRepository.deleteById(id);
    }
}

