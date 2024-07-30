package com.shotty.shotty.service;

import com.shotty.shotty.Domain.User;
import com.shotty.shotty.dto.EncryptedUserDto;
import com.shotty.shotty.dto.UserResponseDto;
import com.shotty.shotty.exception.custom_exception.user.UserIdDuplicateException;
import com.shotty.shotty.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto register(EncryptedUserDto encryptedUserDto) {
        User user;
        try {
            user = userRepository.save(User.from(encryptedUserDto));
        } catch (DataIntegrityViolationException e) {
            throw new UserIdDuplicateException("이미 존재하는 사용자입니다.");
        }

        return UserResponseDto.from(user);
    }
}
