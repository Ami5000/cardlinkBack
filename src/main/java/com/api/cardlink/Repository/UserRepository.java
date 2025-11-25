package com.api.cardlink.Repository;

import com.api.cardlink.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository

public interface UserRepository  extends JpaRepository <User, UUID> {

    //Rechercher un utilisateur par:
    Optional<User> findByFirstName(String firstname);
    Optional<User> findByLastName(String lastname);
    Optional<User> findByUserId(UUID userid);

    //Authentification
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);




}
