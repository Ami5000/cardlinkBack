package com.api.cardlink.Service;

import com.api.cardlink.Dto.UserDto.ConnectionDto;
import com.api.cardlink.Dto.UserDto.UserRequestDto;
import com.api.cardlink.Dto.UserDto.UserResponseDto;
import com.api.cardlink.Entity.User;
import com.api.cardlink.Repository.UserRepository;
import com.api.cardlink.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public boolean checkUserById(String id){
        if (userRepository.findByUserId(UUID.fromString(id)).isPresent()){
            throw new RuntimeException("l'utilisateur avec cet id existe deja dans la base de donnée");
        }
        return true;
    }
    public UserResponseDto create(UserRequestDto userRequestDto){
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()){
            throw new RuntimeException("l'utilisateur avec cet mail existe deja dans la base de donnée");
        }
        User user= userMapper.toEntity(userRequestDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }
    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
    @Transactional
    public  void delete(String id){
        if(!checkUserById(id)) return ;
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(()-> new RuntimeException("Utilisateur not found"));
        userRepository.delete(user);
    }

    public boolean connexion(ConnectionDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur not found"));
        if (user.getPassword().equals(request.getPassword())) {
            return true;
        }
        return false;

    }

    public UserResponseDto update(String id, UserRequestDto dto) {
        if (userRepository.findByUserId(UUID.fromString(id)).isPresent()){
            throw new RuntimeException("l'utilisateur avec cet id existe deja dans la base de donnée");
        }
        User user = User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .avatarUrl(dto.getAvatarUrl())
        .build();

        return userMapper.toDto(userRepository.save(user));
    }

    public UserResponseDto getUserById(String id) {
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(()-> new RuntimeException("Utilisateur non trouver"));
        return userMapper.toDto(user);

    }
}



