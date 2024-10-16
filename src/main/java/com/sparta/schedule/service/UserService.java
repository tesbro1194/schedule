package com.sparta.schedule.service;

import com.sparta.schedule.dto.SharerRequestDto;
import com.sparta.schedule.dto.UserRequestDto;
import com.sparta.schedule.dto.UserResponseDto;
import com.sparta.schedule.entity.Sharer;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.SharerRepository;
import com.sparta.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SharerRepository sharerRepository;

    public void createUser(UserRequestDto requestDto) {
        userRepository.save(new User(requestDto));
    }

    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll().stream().map(user -> new UserResponseDto(user)).toList();
    }

    public String updateUser(Long id, UserRequestDto requestDto) {
        User user = findUserById(id);
        return user.update(requestDto);
    }

    public String deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.deleteById(id);
        return "유저 정보가 삭제되었습니다";
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 유저는 존재하지 않습니다")
        );
    }

    public void registerSharer(SharerRequestDto requestDto) {
        List<User> userList = requestDto.getUserList();
        for (User user : userList) {
            Sharer sharer = new Sharer();
            sharer.setUser(user);
            sharerRepository.save(sharer);
        }
    }
}